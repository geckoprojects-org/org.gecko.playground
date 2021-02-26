package org.gecko.playground.tasks.annotations;

import org.gecko.playground.tasks.TaskConstants;
import org.osgi.annotation.bundle.Requirement;

@Requirement(namespace = TaskConstants.CAPABILITY_TASK, name = TaskConstants.CAPABILITY_TASK_NAME)
public @interface RequireTasks {

}
