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
package org.gecko.playground.extender.activator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.osgi.annotation.bundle.Header;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

@Header(name = "Bundle-Activator", value = "org.gecko.playground.extender.activator.Activator")
public class Activator implements BundleActivator {

	final Map<Bundle, ServiceRegistration<?>> registrations = new ConcurrentHashMap<>();
	

	private BundleTracker<Bundle> tracker;

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Activating Stupid Component Service Runtime");
		tracker = new BundleTracker<>(context, Bundle.ACTIVE|Bundle.STARTING|Bundle.STOPPING, new BundleTrackerCustomizer<Bundle>() {


			@Override
			public Bundle addingBundle(Bundle bundle, BundleEvent event) {
				String instruction = bundle.getHeaders().get("Stupid-DS-Class");
				if(instruction != null) { 
					System.out.println("Header " + instruction);
					String[] split = instruction.split(";");
					String clazzName = split[0];
					Dictionary<String, String> properties = new Hashtable<>();
					if(split.length > 1) {
						for(int i = 1; i < split.length; i++) {
							String prop = split[i];
							String[] strings = prop.split("=");
							if(strings.length == 2) {
								properties.put(strings[0], strings[1]);
							} else {
								System.err.println("Property " + prop + " is unparsable");
							}
							
						}
					}
					
					BundleWiring adapt = bundle.adapt(BundleWiring.class);
					try {
						Class<?> clazz = adapt.getClassLoader().loadClass(clazzName);
						Constructor<?> constructor = clazz.getConstructor();
						Object newInstance = constructor.newInstance(null);
						Class<?>[] interfaces = clazz.getInterfaces();
						String[] services = new String[] {clazzName};
						if(interfaces.length > 0) {
							List<String> list = Arrays.asList(interfaces).stream().map(Class::getName).collect(Collectors.toList());
							services = list.toArray(new String[interfaces.length]);
						}
						ServiceRegistration<?> registerService = bundle.getBundleContext().registerService(services, newInstance, properties);
						registrations.put(bundle, registerService);
					} catch (ClassNotFoundException e) {
						System.err.println(instruction + " not found in " + bundle.getSymbolicName());
					} catch (NoSuchMethodException e) {
						System.err.println(instruction + " does not have a no Args constructor");
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					return bundle;
				}
				return null;
			}

			@Override
			public void modifiedBundle(Bundle bundle, BundleEvent event, Bundle object) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void removedBundle(Bundle bundle, BundleEvent event, Bundle object) {
				// Services will be removed automatrically
				registrations.remove(bundle);
			}
			
		});
		
		tracker.open();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		tracker.close();
		registrations.values().forEach(ServiceRegistration::unregister);
		registrations.clear();
	}

}
