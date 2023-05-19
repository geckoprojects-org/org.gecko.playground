/**
 * Copyright (c) 2012 - 2022 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.playground.ds.factory.cmd;

import org.gecko.playground.ds.factory.api.Device;
import org.gecko.playground.ds.factory.api.DeviceHandler;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = WorkmodeCommands.class, property = {
		"osgi.command.scope=device", //
		"osgi.command.function=createDevice", 
		"osgi.command.function=deleteDevice"
})
public class WorkmodeCommands {
	
	@Reference
	private DeviceHandler deviceHandler;
	
	/**
	 * Set/ change the work mode
	 * @param workMode the work mode string value
	 */
	public void createDevice(String type, String name) {
		try {
			Device d = deviceHandler.createDevice(type, name);
			System.out.println("Device created: " + d.getType() + "- " + d.getName() + " (" + d.toString() + ")");
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("Error creating device'" + type + "' with message: " + e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public void deleteDevice(String type, String name) {
		try {
			deviceHandler.deleteDevice(type, name);
			System.out.println("Device deleted: " + type + "- " + name);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
