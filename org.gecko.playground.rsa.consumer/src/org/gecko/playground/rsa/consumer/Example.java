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
package org.gecko.playground.rsa.consumer;

import org.gecko.playground.rsa.api.HelloWorld;
import org.osgi.service.component.annotations.*;

@Component(immediate = true)
public class Example {
	
	@Reference
//	@Reference(target = "(service.imported=true)")
	private HelloWorld hello;
	
	@Activate
	public void activate() {
		System.out.println("Activate HelloWorld-Consumer");
		String name = "Freeze";
		System.out.println("Calling HelloWorld-Service for Mr. " + name + ": ");
		System.out.println("Result: " + hello.sayHello(name));
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("De-activate HelloWorld-Consumer");
	}

	// TODO: class provided by template

}
