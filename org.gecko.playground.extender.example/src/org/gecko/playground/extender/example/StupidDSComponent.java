/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
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
package org.gecko.playground.extender.example;

import org.osgi.annotation.bundle.Header;
import org.gecko.playground.log.Log;

/**
 * 
 * @author Juergen Albert
 * @since 15 Jan 2024
 */
@Header(name = "Stupid-DS-Class", value = "org.gecko.playground.extender.example.StupidDSComponent;test=blub")
public class StupidDSComponent implements Log{

	/**
	 * Creates a new instance.
	 */
	public StupidDSComponent() {
		System.out.println("Hello World");
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.log.Log#logMessage(java.lang.String)
	 */
	@Override
	public void logMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.log.Log#info(java.lang.String)
	 */
	@Override
	public String info(String message) {
		// TODO Auto-generated method stub
		return null;
	}

}
