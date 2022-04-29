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
package org.gecko.playground.rest;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * 
 * @author mark
 * @since 29.04.2022
 */
@Component(name = "SRS")
public class SimpleResourceStarter {
	
	private ServiceRegistration<SimpleResource> resourceRegistration;

	@Activate
	public void activate(BundleContext ctx) {
		System.out.println("Activate SimpleResourceStarter");
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(JaxrsWhiteboardConstants.JAX_RS_RESOURCE, Boolean.TRUE);
		properties.put(JaxrsWhiteboardConstants.JAX_RS_NAME, "MySimpleResource");
		System.out.println("Register SimpleResource");
		resourceRegistration = ctx.registerService(SimpleResource.class, new SimpleResource(), properties);
		System.out.println("Registered SimpleResource");
	}
	
	@Deactivate
	public void deactivate() {
		if (resourceRegistration != null) {
			System.out.println("De-Register SimpleResource");
			resourceRegistration.unregister();
			System.out.println("De-Registered SimpleResource");
		}
	}
	
}
