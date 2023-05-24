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
		System.out.println("Started activation EXTERNAL 1 ...");
		try {
			Thread.sleep(8000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Finished initialization EXTERNAL 1");
	}

	@Override
	protected void doDeactivate() {
		System.out.println("Started deactivation EXTERNAL 1 ...");
		try {
			Thread.sleep(3000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Finished deactivation EXTERNAL 1");
	}
}
