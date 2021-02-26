package org.gecko.playground.tasks.demo;

import org.gecko.playground.tasks.TaskConstants;
import org.gecko.playground.workmode.WorkmodeConstants;
import org.osgi.service.condition.Condition;

public interface Conditions {
	
	public static final String CONDITION_UI = "(&(" + Condition.CONDITION_ID + "=" + TaskConstants.CONDITION_TASK + ")"
					+ "(" + TaskConstants.ACTIVE_TASKS + "=externalTwoAuth))";
	
	public static final String TARGET_ONLINE = "(&(" + Condition.CONDITION_ID + "=" + WorkmodeConstants.CONDITION_WORKMODE + ")"
					+ "(" + WorkmodeConstants.CONDITION_WORKMODE + "=ONLINE))";

}
