/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package de.gecko.playground.ds.inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * 
 * @author mark
 * @since 14.11.2023
 */
@Component
public class TestConsumer {

	@Activate
	public TestConsumer(@Reference(name="test")ITest test) {
		doSetTest(test);
		System.out.println("activate");
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("de-activate");
	}

	public void doSetTest(ITest test) {
		System.out.println("DO SET " + test.sayHello("Welt"));
	}
	
	@Reference(updated = "modTest", unbind = "unsetTest")
	public void setTest(ITest test) {
		System.out.println(" VIRTUALLY SET " + test.sayHello("Welt"));
	}
	
	public void modTest(ITest test) {
		System.out.println("MODIFIED " + test.sayHello("Welt"));
	}
	
	public void unsetTest(ITest test) {
		System.out.println("UNSET " + test.sayHello("Welt"));
	}
}
