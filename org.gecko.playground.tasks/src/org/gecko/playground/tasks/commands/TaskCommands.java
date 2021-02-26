package org.gecko.playground.tasks.commands;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.gecko.playground.tasks.TaskInfo;
import org.gecko.playground.tasks.TaskService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.promise.Promise;

/**
 * Gogo commands for the activation service
 * showState - shows the state of all activation tasks 
 * showState <taskId> - shows the state of the given task id
 * activate <taskId> - activates the task with the given task id
 * deactivate <taskId> - deactivates the task with the given task id  
 * 
 */
@Component(service = TaskCommands.class, property = {
		"osgi.command.scope=task", //
		"osgi.command.function=showState", //
		"osgi.command.function=activate", //
		"osgi.command.function=deactivate", //
})
public class TaskCommands {

	private static final String TASK_ACTIVATION_FAILED = "Activating task with id '%s' failed with error '%s'"; 
	private static final String TASK_ACTIVATION_START = "Activate task with id '%s' and name '%s' ..."; 
	private static final String TASK_ACTIVATION = "Activated task id '%s' and name '%s' with result '%s'"; 
	private static final String TASK_DEACTIVATION_START = "De-activate task with id '%s' and name '%s' ..."; 
	private static final String TASK_DEACTIVATION_FAILED = "De-activating task with id '%s' failed with error '%s'"; 
	private static final String TASK_DEACTIVATION = "De-activated task id '%s' and name '%s' with result '%s'"; 
	private static final String TASK_STATE = "Task with id '%s' and name '%s' has state '%s'"; 
	private static final String TASK_NOT_FOUND = "Task with id '%s' was not found"; 
	private static final String TASK_STATE_ERROR = "Error showing task state for task with id '%s'"; 
	private static final String TASK_STATES_ERROR = "Error showing all task states"; 

	@Reference
	private TaskService activationService;

	public void showState(String taskId) {
		try {
			TaskInfo taskInfo = activationService.getTaskInfo(taskId);
			if (taskInfo != null) {
				System.out.println(String.format(TASK_STATE, taskId, taskInfo.getName(), taskInfo.getState()));
			} else {
				System.out.println(String.format(TASK_NOT_FOUND, taskId));
			}
		} catch (Exception e) {
			System.out.println(String.format(TASK_STATE_ERROR, taskId));
		}
	}

	public void showState() {
		try {
			Set<String> taskIds = activationService.getTaskIds();
			taskIds.forEach(this::showState);
		} catch (Exception e) {
			System.out.println(TASK_STATES_ERROR);
		}
	}

	public void activate(String taskId) {
		if (taskId == null) {
			System.out.println("Cannot activate a task without an id");
			return;
		}
		try {
			TaskInfo taskInfo = activationService.getTaskInfo(taskId);
			if (taskInfo == null) {
				System.out.println(String.format(TASK_NOT_FOUND, taskId));
				return;
			}
			System.out.println(String.format(TASK_ACTIVATION_START, taskId, taskInfo.getName()));
			Promise<TaskInfo> task = activationService.activateTask(taskId);
			taskInfo = task.getValue();
			System.out.println(String.format(TASK_ACTIVATION, taskId, taskInfo.getId(), taskInfo.getState()));
		} catch (InvocationTargetException e) {
			System.out.println(String.format(TASK_ACTIVATION_FAILED, taskId, e.getCause().getMessage()));
		} catch (Exception e) {
			System.out.println(String.format(TASK_ACTIVATION_FAILED, taskId, e.getMessage()));
		}
	}

	public void deactivate(String taskId) {
		if (taskId == null) {
			System.out.println("Cannot deactivate a task without an id");
			return;
		}
		try {
			TaskInfo taskInfo = activationService.getTaskInfo(taskId);
			if (taskInfo == null) {
				System.out.println(String.format(TASK_NOT_FOUND, taskId));
				return;
			}
			System.out.println(String.format(TASK_DEACTIVATION_START, taskId, taskInfo.getName()));
			Promise<TaskInfo> task = activationService.deactivateTask(taskId);
			taskInfo  = task.getValue();
			System.out.println(String.format(TASK_DEACTIVATION, taskId, taskInfo.getId(), taskInfo.getState()));
		} catch (InvocationTargetException e) {
			System.out.println(String.format(TASK_DEACTIVATION_FAILED, taskId, e.getCause().getMessage()));
		} catch (Exception e) {
			System.out.println(String.format(TASK_DEACTIVATION_FAILED, taskId, e.getMessage()));
		}
	}

}
