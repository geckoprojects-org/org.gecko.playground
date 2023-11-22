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
package org.gecko.vaadin.example.ece;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.Executors;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.util.promise.PromiseFactory;

/**
 * Component that registers the Vaadin View component
 * @author Mark Hoffmann
 * @since 09.10.2023
 */
@Component
public class EclipseConComponent {

	@Activate
	public void activate(BundleContext ctx) {
		final Dictionary<String, Object> properties = new Hashtable<>();
		properties.put("menu.name", "ECE 2023");
		properties.put("vaadin.component", true);
		PromiseFactory pf = new PromiseFactory(Executors.newCachedThreadPool());
		pf.resolved("").delay(15000l).map((s)->{
			System.out.println("Register Eclipsecon View");
			return ctx.registerService(new String[]{EclipseconView.class.getName(), com.vaadin.flow.component.Component.class.getName()}, new EclipseconView(), properties);
		}).delay(3000).onSuccess(r->{
			System.out.println("Set EclipseconView filter: main=test");
			properties.put("main", "test");
			r.setProperties(properties);
		});
	}
}
