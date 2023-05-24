package org.gecko.playground.tasks.demo;

import org.gecko.playground.tasks.AbstractTask;
import org.gecko.playground.tasks.Task;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.condition.Condition;

@Component(service = Task.class, reference = {
		@Reference(name = "onlineCondition", service = Condition.class, target = Conditions.TARGET_ONLINE)
})
public class OnlineAuthenticatedExampleInitializationTask extends AbstractTask {
	
	@Override
	public void doActivate() {
		System.out.println("Started initialization EXTERNAL 3 Workmode ONLINE ...");
		try {
			Thread.sleep(5000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Finished initialization EXTERNAL 3 Workmode ONLINE");
	}

	@Override
	public void doDeactivate() {
		System.out.println("Started deactivation EXTERNAL 3 Workmode ONLINE ...");
		try {
			Thread.sleep(5000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Finished deactivation EXTERNAL 3 Workmode ONLINE");
	}

	@Override
	public String getName() {
		return "EXTERNAL 3 - INITIALIZATION TASK (Workmode ONLINE)";
	}
	
	@Override
	public String getId() {
		return "externalOnline";
	}

}
