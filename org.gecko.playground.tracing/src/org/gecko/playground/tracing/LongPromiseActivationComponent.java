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

import java.util.concurrent.Executors;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.promise.PromiseFactory;

/**
 * 
 * @author mark
 * @since 05.09.2023
 */
@Component
public class LongPromiseActivationComponent {

	private final PromiseFactory pf = new PromiseFactory(Executors.newCachedThreadPool());

	@Activate
	public void activate() {
		pf.submit(()->{
			System.out.println("Activate long promise ...");
			Thread.sleep(750);
			return null;
		}).onResolve(()->System.out.println("Activated long promise"));
	}

	@Deactivate
	public void deactivate() {
		pf.submit(()->{
			System.out.println("De-activate long promise ...");
			Thread.sleep(350);
			return null;
		}).onResolve(()->System.out.println("De-activated long promise"));
	}

}
