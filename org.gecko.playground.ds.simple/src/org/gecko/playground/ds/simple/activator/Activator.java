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
package org.gecko.playground.ds.simple.activator;

import java.util.Dictionary;
import java.util.Hashtable;

import org.gecko.playground.ds.simple.ConsoleLog;
import org.gecko.playground.log.Log;
import org.osgi.annotation.bundle.Header;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

@Header(name = "Bundle-Activator", value = "org.gecko.playground.ds.simple.activator.Activator")
public class Activator implements BundleActivator {

	private ServiceRegistration<Log> registerService;

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Test");
		
		Dictionary<String, String> props = new Hashtable<>();
		props.put("some prop", "via Activator" );
		
		ConsoleLog log = new ConsoleLog("Activator");
		
		registerService = context.registerService(Log.class, log, props);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		registerService.unregister();
	}

}
