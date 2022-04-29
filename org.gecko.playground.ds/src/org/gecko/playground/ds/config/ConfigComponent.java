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
package org.gecko.playground.ds.config;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * 
 * @author mark
 * @since 29.04.2022
 */
@Component(name = "MyFancyConfig", configurationPolicy = ConfigurationPolicy.REQUIRE)
public class ConfigComponent {
	
	@Activate
	public void activate(MyFancyConfig c) {
		System.out.println("Activate " + c.name() + " from " + c.year() + "(" + c.description() + ") from instance " + this);
	}
	
	@Modified
	public void modified(MyFancyConfig c) {
		System.out.println("Modified " + c.name() + " from " + c.year() + "(" + c.description() + ") from instance " + this);
		
	}
	
	@Deactivate
	public void deactivate(MyFancyConfig c) {
		System.out.println("De-Activate " + c.name() + " from " + c.year() + "(" + c.description() + ") from instance " + this);
		
	}

}
