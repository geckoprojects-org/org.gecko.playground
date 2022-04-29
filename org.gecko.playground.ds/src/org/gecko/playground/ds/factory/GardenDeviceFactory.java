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
package org.gecko.playground.ds.factory;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * 
 * @author mark
 * @since 29.04.2022
 */
@Component(factory = "GARDEN", service = Device.class, property = "type=GARDEN")
public class GardenDeviceFactory implements Device {
	
	private static final String type = "GARDEN-DEVICE";
	private String name;
	
	@Activate
	public void activate(Map<String, Object> props) {
		name = (String) props.get("name");
		System.out.println("Activate Instance " + getType() + " - " + name);
		
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("Deactivate Instance " + getType() + " - " + getName());
		stop();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.ds.factory.Device#getType()
	 */
	@Override
	public String getType() {
		return type;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.ds.factory.Device#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.ds.factory.Device#initialize()
	 */
	@Override
	public void initialize() {
		System.out.println("INIT " + getType() + " - " + getName());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.ds.factory.Device#start()
	 */
	@Override
	public void start() {
		System.out.println("START " + getType() + " - " + getName());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.ds.factory.Device#stop()
	 */
	@Override
	public void stop() {
		System.out.println("STOP " + getType() + " - " + getName());
	}

}
