package org.gecko.playground.workmode.command;

import org.gecko.playground.workmode.Workmode;
import org.gecko.playground.workmode.WorkmodeService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

/**
 * Commands for the core bundle
 * * setting the work mode - setWorkMode
 * * getting the work mode - getWorkMode
 */
@Component(service = WorkmodeCommands.class, property = {
		"osgi.command.scope=workmode", //
		"osgi.command.function=setWorkmode", 
		"osgi.command.function=getWorkmode"
})
public class WorkmodeCommands {
	
	@Reference(service = LoggerFactory.class)
	private Logger logger;
	@Reference
	private WorkmodeService workModeService;
	
	/**
	 * Set/ change the work mode
	 * @param workMode the work mode string value
	 */
	public void setWorkmode(String workMode) {
		try {
			Workmode mode = Workmode.valueOf(workMode);
			if (mode == null) {
				mode = Workmode.OFFLINE;
			}
			workModeService.setWorkmode(mode);
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("Error setting work mode '" + workMode + "' with message: " + e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public void getWorkmode() {
		try {
			Workmode workmode = workModeService.getWorkmode();
			System.out.println("Current work mode is '" + workmode + "'");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
