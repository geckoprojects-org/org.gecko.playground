package org.gecko.playground.tasks;

/**
 * Info interface with basic information about an activation task.
 * 
 */
public interface TaskInfo {
	
	/**
	 * Returns the task id
	 * @return the task id, must not be <code>null</code>
	 */
	public String getId();
	
	/**
	 * Returns the task name
	 * @return the task name
	 */
	public String getName();
	
	/**
	 * Returns the task state
	 * @return the task state, must no be <code>null</code>
	 */
	public TaskState getState();
	
}
