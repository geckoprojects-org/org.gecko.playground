package org.gecko.playground.tasks;

import org.osgi.util.promise.Promise;

/**
 * An activation task. A task can be activated or deactivated.
 * To make use of this interface, users should use the {@link AbstractTask}, that
 * provides a ready to use implementation. This should be preferred.
 * 
 */
public interface Task extends TaskInfo {
	
	/**
	 * Activate a task. The {@link Promise} resolves as soon the task has finished. On errors the promise fails. 
	 * The task info instance that is returned, represents the activation result. 
	 * @return a {@link Promise} with a task info instance that represents the activation result
	 */
	public Promise<TaskInfo> activateTask();
	
	/**
	 * De-activate a task. The {@link Promise} resolves as soon the task de-activation has finished. On errors the promise fails. 
	 * The task info instance that is returned, represents the de-activation result. 
	 * @return a {@link Promise} with a task info instance that represents the de-activation result
	 */
	public Promise<TaskInfo> deactivateTask();
	
}
