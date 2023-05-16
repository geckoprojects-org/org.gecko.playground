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
package org.gecko.playground.ds.factory;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.gecko.playground.ds.factory.api.Device;
import org.gecko.playground.ds.factory.api.DeviceHandler;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

@Component(immediate = true)
public class DeviceHandlerService implements DeviceHandler {
	
	private Map<String, ComponentFactory<Device>> factoryMap = new ConcurrentHashMap<String, ComponentFactory<Device>>();
	private Map<String, ComponentInstance<Device>> instanceMap = new ConcurrentHashMap<String, ComponentInstance<Device>>();
	
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addComponentFactory(ComponentFactory<Device> factory, Map<String, Object> properties) {
		String type = (String) properties.get("component.factory");
		factoryMap.put(type, factory);
	}
	
	public void removeComponentFactory(ComponentFactory<Device> factory, Map<String, Object> properties) {
		String type = (String) properties.get("component.factory");
		factoryMap.remove(type);
	}
	
	ComponentInstance<Device> createInstance(String type, String name) {
		ComponentFactory<Device> factory = factoryMap.get(type);
		if (factory == null) {
			throw new IllegalStateException("No factory found for type " + type);
		}
		Dictionary<String, Object> props = new Hashtable<String, Object>();
		props.put("name", name);
		return factory.newInstance(props);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.ds.factory.DeviceHandler#createDevice(java.lang.String, java.lang.String)
	 */
	@Override
	public Device createDevice(String type, String name) {
		ComponentInstance<Device> device = createInstance(type, name);
		instanceMap.put(type + "_" + name, device);
		return device.getInstance();
		
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.ds.factory.DeviceHandler#deleteDevice(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteDevice(String type, String name) {
		ComponentInstance<Device> device = instanceMap.remove(type + "_" + name);
		if (device != null) {
			System.out.println("Dispose device " + type + " - " + name);
			device.dispose();
		} else {
			System.out.println("No device to delete type " + type + " - " + name);
		}
	}

}
