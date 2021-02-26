package org.gecko.playground.tasks.spi;

import java.util.concurrent.Executors;

import org.gecko.playground.tasks.Task;
import org.gecko.playground.tasks.TaskInfo;
import org.gecko.playground.tasks.TaskState;
import org.osgi.util.promise.Promise;
import org.osgi.util.promise.PromiseFactory;

/**
 * This is a replacement implementation for task services that are removed from the service registry.
 * To keep their latest information, this object is used.
 * 
 */
public final class RemovedTask implements Task {
	
	private static PromiseFactory pf = new PromiseFactory(Executors.newSingleThreadExecutor());
	private final TaskInfo taskInfo;
	
	/**
	 * Creates a corresponding task instance
	 * @param taskInfo the task information from the original task
	 * @return the task instance
	 */
	public static Task createRemovedTask(TaskInfo taskInfo) {
		if (taskInfo == null) {
			throw new IllegalArgumentException("Task info must not be null, to create a remove activation task");
		}
		return new RemovedTask(ProxyTaskInfo.create(taskInfo));
	}
	
	private RemovedTask(TaskInfo taskInfo) {
		this.taskInfo = taskInfo;
	}
	
	@Override
	public String getId() {
		return taskInfo.getId();
	}

	@Override
	public String getName() {
		return taskInfo.getName();
	}

	@Override
	public Promise<TaskInfo> activateTask() {
		return pf.resolved(this);
	}

	@Override
	public Promise<TaskInfo> deactivateTask() {
		return pf.resolved(this);
	}

	@Override
	public TaskState getState() {
		return TaskState.REMOVED;
	}

}
