package org.gecko.playground.tasks;

import java.util.Set;

import org.osgi.util.promise.Promise;

/**
 * Service to interact with the activation task engine. Task information can be read, as well as triggering activation or de-activation
 * 
 */
public interface TaskService {
	
	/**
	 * Returns the {@link TaskInfo} instance for the given task id or <code>null</code>,
	 * if nothing was found
	 * @param taskId the task id to return the info object for
	 * @return the instance or <code>null</code>
	 */
	TaskInfo getTaskInfo(String taskId);
	
	/**
	 * Returns a set of registered task id's or en empty set
	 * @return a set of registered task id's or en empty set
	 */
	Set<String> getTaskIds();
	
	/**
	 * Activates a task. The task must be in state ACTIVE, INACTIVE or ERROR. Otherwise the current state is resolved through the task info instance via {@link Promise}.
	 * The promise resolves as soon as the activation has finished or it fails with the corresponding {@link Throwable}
	 * @param taskId the task id of the task to activate
	 * @return the {@link Promise} with the activation result
	 */
	Promise<TaskInfo> activateTask(String taskId);
	
	/**
	 * De-activates a task. The task must be in state ACTIVE, INACTIVE or ERROR. Otherwise the current state is resolved through the task info instance via {@link Promise}.
	 * The promise resolves as soon as the de-activation has finished or it fails with the corresponding {@link Throwable}
	 * @param taskId the task id of the task to de-activate
	 * @return the {@link Promise} with the de-activation result
	 */
	Promise<TaskInfo> deactivateTask(String taskId); 

}
