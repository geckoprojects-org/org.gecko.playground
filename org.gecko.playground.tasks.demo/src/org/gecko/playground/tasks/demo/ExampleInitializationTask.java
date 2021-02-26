package org.gecko.playground.tasks.demo;

import org.gecko.playground.tasks.AbstractTask;
import org.gecko.playground.tasks.Task;
import org.gecko.playground.tasks.annotations.RequireTasks;
import org.gecko.playground.workmode.annotations.RequireWorkmode;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

@RequireTasks
@RequireWorkmode
@Component(service = Task.class)
public class ExampleInitializationTask extends AbstractTask {

	@Reference(service = LoggerFactory.class)
	private Logger logger;
	
	@Override
	public String getName() {
		return "EXTERNAL 1 - INITIALIZATION TASK";
	}
	
	@Override
	public String getId() {
		return "externalTest";
	}
	
	@Override
	protected void doActivate() {
		logger.info("Started activation EXTERNAL 1 ...");
		try {
			Thread.sleep(8000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("Finished initialization EXTERNAL 1");
	}

	@Override
	protected void doDeactivate() {
		logger.info("Started deactivation EXTERNAL 1 ...");
		try {
			Thread.sleep(3000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("Finished deactivation EXTERNAL 1");
	}
}
