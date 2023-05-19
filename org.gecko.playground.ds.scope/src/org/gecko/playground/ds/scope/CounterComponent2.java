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
package org.gecko.playground.ds.scope;

import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component
public class CounterComponent2 {
	
//	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
//	@Reference(scope = ReferenceScope.PROTOTYPE)
	@Reference
	private Counter counter;
	
	@Activate
	public void activate() {
		if (Objects.isNull(counter)) {
			System.out.println("Counter 2: No counter available");
			return;
		}
		for (int i = 0; i < 3; i++) {
			System.out.println("Counter 2: " + counter.getCurrentCount() + " with instance " + counter.toString());
		}
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("------ Deactivate Counter 2 ------");
	}

}
