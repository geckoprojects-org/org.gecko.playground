package org.gecko.playground.tasks;

/**
 * Enum for task states
 * 
 */
public enum TaskState {
	
	// A task is not activate
	INACTIVE,
	// A task is currently activating
	ACTIVATING,
	// A task was activated
	ACTIVE,
	// A task is de-activating
	DEACTIVATING,
	// A task is in state ERROR during activation or de-activation 
	ERROR,
	// A task service was removed from the service registry, maybe a condition forces this serice to be removed from the service registry
	REMOVED,

}
