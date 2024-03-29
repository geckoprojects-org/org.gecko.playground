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
package org.gecko.playground.vaadin.service;

import org.osgi.service.component.annotations.Component;

@Component
public class Greeter implements GreeterService {

	@Override
	public String greet(String name) {
		return "Hello " + name;
	}

}
