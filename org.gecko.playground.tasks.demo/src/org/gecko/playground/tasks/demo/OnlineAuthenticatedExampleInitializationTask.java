package org.gecko.playground.tasks.demo;

import org.gecko.playground.tasks.AbstractTask;
import org.gecko.playground.tasks.Task;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.condition.Condition;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;


@Component(service = Task.class, reference = {
		@Reference(name = "onlineCondition", service = Condition.class, target = Conditions.TARGET_ONLINE)
})
public class OnlineAuthenticatedExampleInitializationTask extends AbstractTask {
	
	@Reference(service = LoggerFactory.class)
	private Logger logger;

	@Override
	public void doActivate() {
		logger.info("Started initialization EXTERNAL 3 Workmode ONLINE ...");
		try {
			Thread.sleep(5000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("Finished initialization EXTERNAL 3 Workmode ONLINE");
	}

	@Override
	public void doDeactivate() {
		logger.info("Started deactivation EXTERNAL 3 Workmode ONLINE ...");
		try {
			Thread.sleep(5000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Finished deactivation EXTERNAL 3 Workmode ONLINE");
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
