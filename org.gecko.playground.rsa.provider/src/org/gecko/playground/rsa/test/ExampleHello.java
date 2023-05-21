/**
 * Copyright (c) 2012 - 2021 Paremus Ltd., Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 * 		Paremus Ltd. - initial API and implementation
 *      Data In Motion
 */
package org.gecko.playground.rsa.test;

import org.gecko.playground.rsa.api.HelloWorld;
import org.osgi.service.component.annotations.*;
import org.osgi.service.component.annotations.Component;

@Component(service = HelloWorld.class ,immediate = true, property = {"service.exported.configs=com.paremus.dosgi.net", "service.exported.interfaces=*", "com.paremus.dosgi.scope=global", "com.paremus.dosgi.target.clusters=DIMC"})
//@Component(service = HelloWorld.class ,immediate = true, property = {"service.exported.configs=com.paremus.dosgi.net", "service.exported.interfaces=*", "com.paremus.dosgi.scope=universal"})
public class ExampleHello implements HelloWorld {
	
	private static String GREETING = "Hello World, dear '%s'"; 
	
	@Activate
	public void activate() {
		System.out.println("Activate HelloWorld ExampleHello-Implementation");
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("De-activate HelloWorld ExampleHellp-Implementation");
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.rsa.test.api.HelloWorld#sayHello(java.lang.String)
	 */
	@Override
	public String sayHello(String name) {
		name = name == null ? "Nobody" : name;
		return String.format(GREETING, name);
	}

	// TODO: class provided by template

}
