package org.gecko.playground.tasks.spi;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import org.gecko.playground.tasks.TaskConstants;
import org.gecko.playground.tasks.Task;
import org.gecko.playground.tasks.TaskInfo;
import org.gecko.playground.tasks.TaskService;
import org.osgi.annotation.bundle.Capability;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.condition.Condition;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;
import org.osgi.util.promise.Promise;
import org.osgi.util.promise.PromiseFactory;

/**
 * A service component that handles the activation tasks.
 * 
 * services of type {@link Task} are injected into this whiteboard.
 *
 */
@Capability(namespace = TaskConstants.CAPABILITY_TASK, name = TaskConstants.CAPABILITY_TASK_NAME, version = "1.0")
@Component(name = "DefaultActivationComponent", immediate = true)
public class TaskComponent implements TaskService {

	private Logger logger;
	private Map<String, Task> taskMap = new ConcurrentHashMap<>();
	private List<String> activeTasks = new LinkedList<>();
	private AtomicReference<ServiceRegistration<Condition>> activationConditionRegistration = new AtomicReference<ServiceRegistration<Condition>>();

	/**
	 * Constructor injection to have the logger available
	 * @param logger the logger
	 */
	@Activate
	public TaskComponent(@Reference(service = LoggerFactory.class) Logger logger) {
		this.logger = logger;
	}

	/**
	 * Called on service activation
	 * @param context the {@link BundleContext}
	 */
	@Activate
	public void activate(BundleContext context) {
		logger.info("Register CORE activation condition");
		// register a condition service for the activation state
		Dictionary<String,Object> properties = getConditionProperties();
		ServiceRegistration<Condition> registration = context.registerService(Condition.class, Condition.INSTANCE, properties);
		activationConditionRegistration.set(registration);
	}

	/**
	 * Called on service de-activation
	 */
	@Deactivate
	public void deactivate() {
		logger.info("De-activate CORE initialization component");
		// remove condition
		ServiceRegistration<Condition> registration = activationConditionRegistration.getAndSet(null);
		if (registration != null) {
			registration.unregister();
		}
		taskMap.clear();
	}

	/**
	 * Injection point for OSGi to inject {@link Task} services
	 * @param task the task to be added
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addInitTask(Task task) {
		logger.debug("Added initializing task '{}'", task.getId());
		synchronized (taskMap) {
			taskMap.put(task.getId() , task);
		}
		/*
		 * Activate the task. There is a timeout defined for the activation. After that timeout the activation fails.
		 * On success we update the condition service and provide him the task id that he uses then as service property value
		 * This happens asynchronous.
		 */
		task.activateTask()
				.timeout(10000l)
				.onSuccess(i->activeTasks.add(i.getId()))
				.onResolve(this::updateCondition)
				.onFailure(t->logger.error("Activation of task '{}' failed: ", task.getId(), t));
	}

	/**
	 * Injection point for OSGi to remove already injected {@link Task} services.
	 * the declaration for OSGi is done implicitly because of the 'remove' in the methodName.
	 * @param task the task to be removed
	 */
	public void removeInitTask(Task task) {
		logger.info("Removed initializing task '{}'", task.getId());
		/*
		 * De-activate the task. There is a timeout defined for the de-activation. After that timeout the de-activation fails.
		 * On success we remove the id from the active tasks. Wen update the condition service and provide him the 
		 * current list with active task id.
		 * Because this service is removed from the OSGi service registry, we replace the task with a dummy object,
		 * so that we have still information about the task and its last state
		 * This happens asynchronous.
		 */
		synchronized (activeTasks) {
			activeTasks.remove(task.getId());
			logger.info("Updating condition '{}'", task.getId());
			updateCondition();
		}
		task.deactivateTask()
				.timeout(10000l)
				.onFailure(t->logger.error("Deactivation of task '{}' failed: ", task.getId(), t))
				.onResolve(()->{
					synchronized (taskMap) {
						String id = task.getId();
						Task current = taskMap.get(id);
						/* 
						 * If the task is still the same and there is no new one, 
						 * we replace the task with the placeholder
						 */
						if (task.equals(current)) {
							taskMap.replace(id, RemovedTask.createRemovedTask(task));
						} 
					}
				});
	}

	/**
	 * Update the service properties for the condition service
	 */
	private void updateCondition() {
		if (activationConditionRegistration == null) {
			logger.warn("The condition for activation tasks was not registered but expected to.");
			return;
		}
		ServiceRegistration<Condition> registration = activationConditionRegistration.get();
		if (registration != null) {
			Dictionary<String,Object> properties = getConditionProperties();
			registration.setProperties(properties);
			logger.info("Updated condition '{}'", properties.toString());
		} else {
			logger.warn("The condition registration is not available (anymore).");
		}
	}

	/**
	 * Returns the condition properties
	 * @return the condition properties
	 */
	private Dictionary<String, Object> getConditionProperties() {
		Dictionary<String, Object> properties = new Hashtable<String, Object>();
		properties.put(Condition.CONDITION_ID, TaskConstants.CONDITION_TASK);
		synchronized (activeTasks) {
			// FELIX-6335
//			properties.put(Constants.CONDITION_ACTIVATION_TASKS, Collections.unmodifiableList(activeTasks));
			properties.put(TaskConstants.ACTIVE_TASKS, new ArrayList<>(activeTasks));
		}
		return properties;
	}

	@Override
	public TaskInfo getTaskInfo(String taskId) {
		synchronized (taskMap) {
			Task task = taskMap.get(taskId);
			return task == null ? null : ProxyTaskInfo.create(task);
		}
	}

	@Override
	public Set<String> getTaskIds() {
		synchronized (taskMap) {
			return new HashSet<String>(taskMap.keySet());
		}
	}
	
	@Override
	public Promise<TaskInfo> activateTask(String taskId) {
		PromiseFactory pf = new PromiseFactory(Executors.newSingleThreadExecutor());
		final Task task;
		synchronized (taskMap) {
			task = taskMap.get(taskId);
		}
		if (task == null) {
			return pf.failed(new IllegalStateException("A task with id '" + taskId + "' doesn't exist!"));
		}
		switch (task.getState()) {
		case ACTIVATING:
		case DEACTIVATING:
		case REMOVED:
			return pf.resolved(ProxyTaskInfo.create(task));
		default:
			break;
		}
		return task.activateTask().timeout(30000l);
	}

	@Override
	public Promise<TaskInfo> deactivateTask(String taskId) {
		PromiseFactory pf = new PromiseFactory(Executors.newSingleThreadExecutor());
		final Task task;
		synchronized (taskMap) {
			task = taskMap.get(taskId);
		}
		if (task == null) {
			return pf.failed(new IllegalStateException("A task with id '" + taskId + "' doesn't exist!"));
		}
		switch (task.getState()) {
		case ACTIVATING:
		case DEACTIVATING:
		case REMOVED:
			return pf.resolved(ProxyTaskInfo.create(task));
		default:
			break;
		}
		return task.deactivateTask().timeout(30000l);
	}
	
}