package org.gecko.playground.tasks.spi;

import org.gecko.playground.tasks.TaskInfo;
import org.gecko.playground.tasks.TaskState;

/**
 * A task info that is used as proxy instead of making the task instance itself public.
 *  
 */
public class ProxyTaskInfo implements TaskInfo {
	
	private final String id;
	private final String name;
	private final TaskState state;
	
	/**
	 * Creates a proxied task info from a given task info
	 * @param taskInfo the original {@link TaskInfo} instance
	 * @return the proxy instance
	 */
	public static TaskInfo create(TaskInfo taskInfo) {
		if (taskInfo == null) {
			throw new IllegalArgumentException("Task info instance must not be null");
		}
		return new ProxyTaskInfo(taskInfo);
	}
	
	/**
	 * Creates a proxied task info from a given task info and a certain state
	 * @param taskInfo the original {@link TaskInfo} instance
	 * @param state the state of the proxy
	 * @return the proxy instance
	 */
	public static TaskInfo create(TaskInfo taskInfo, TaskState state) {
		if (taskInfo == null) {
			throw new IllegalArgumentException("Task info instance must not be null");
		}
		return state == null ? new ProxyTaskInfo(taskInfo) : new ProxyTaskInfo(taskInfo, state);
	}
	
	ProxyTaskInfo(TaskInfo taskInfo) {
		this(taskInfo, taskInfo.getState());
	}
	
	ProxyTaskInfo(TaskInfo taskInfo, TaskState state) {
		id = taskInfo.getId();
		name = taskInfo.getName();
		this.state = state;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public TaskState getState() {
		return state;
	}

}
