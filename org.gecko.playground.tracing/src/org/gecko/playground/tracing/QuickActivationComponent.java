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
package org.gecko.playground.tracing;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * 
 * @author mark
 * @since 05.09.2023
 */
@Component
public class QuickActivationComponent {
	
	@Activate
	public void activate() {
		try {
			System.out.println("Activate Quick ...");
			Thread.sleep(15);
			System.out.println("Activated Quick");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Deactivate
	public void deactivate() {
		try {
			System.out.println("De-activate Quick ...");
			Thread.sleep(15);
			System.out.println("De-activated Quick");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
