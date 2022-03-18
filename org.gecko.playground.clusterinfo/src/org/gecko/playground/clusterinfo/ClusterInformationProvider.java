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
package org.gecko.playground.clusterinfo;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.clusterinfo.FrameworkNodeStatus;
import org.osgi.service.clusterinfo.NodeStatus;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;

/**
 * 
 * @author mark
 * @since 18.03.2022
 */
@Component(configurationPid = "PlaygroundClusterInformation", configurationPolicy = ConfigurationPolicy.REQUIRE)
public class ClusterInformationProvider {
	
	private final Dictionary<String, Object> properties = new Hashtable<>();
	private FrameworkNodeStatus manager;
	private ServiceRegistration<?> clusterInfoRegistration;
	
	@Activate
	public void activate(BundleContext context, Map<String, String> config) {
		manager = new FrameworkManager(context);
		// the id should be the framework uuid
		properties.put("osgi.clusterinfo.id", context.getProperty(Constants.FRAMEWORK_UUID).toString());
		
		String s;
		
		s = config.get("cluster.name");
		properties.put("osgi.clusterinfo.cluster", s == null ? "default" : s);
		
		properties.put("osgi.clusterinfo.endpoint", new String[]{});
		properties.put("osgi.clusterinfo.private.endpoint", new String[]{});
		
		s = config.get("cluster.vendor");
		properties.put("osgi.clusterinfo.vendor", s == null ? "Playground" : s);
		properties.put("osgi.clusterinfo.version", "1.0.0");
		
		s = config.get("cluster.tags");
		if(s != null)
			properties.put("osgi.clusterinfo.tags", s.split(","));
		
		// FrameworkNodeStatus properties
		// OSGi Framework properties
		properties.put(Constants.FRAMEWORK_UUID, context.getProperty(Constants.FRAMEWORK_UUID).toString());
		properties.put(Constants.FRAMEWORK_VERSION, context.getProperty(Constants.FRAMEWORK_VERSION));
		properties.put(Constants.FRAMEWORK_PROCESSOR, context.getProperty(Constants.FRAMEWORK_PROCESSOR));
		properties.put(Constants.FRAMEWORK_OS_NAME, context.getProperty(Constants.FRAMEWORK_OS_NAME));
		properties.put(Constants.FRAMEWORK_OS_VERSION, context.getProperty(Constants.FRAMEWORK_OS_VERSION));
		
		// Java properties
		properties.put("java.version", System.getProperty("java.version"));
		properties.put("java.vm.version", System.getProperty("java.vm.version"));
		properties.put("java.specification.version", System.getProperty("java.specification.version"));
		properties.put("java.runtime.version", System.getProperty("java.runtime.version"));
		
		// Export the services via RSA
		if (!"local".equalsIgnoreCase(config.get("cluster.name"))) {
			properties.put("service.exported.interfaces","*");
		}
		clusterInfoRegistration = context.registerService(new String[] {FrameworkNodeStatus.class.getName(), NodeStatus.class.getName()}, manager, properties);
	}
	
	@Deactivate
	public void deactivate() {
		if (clusterInfoRegistration != null) {
			clusterInfoRegistration.unregister();
		}
	}

}
