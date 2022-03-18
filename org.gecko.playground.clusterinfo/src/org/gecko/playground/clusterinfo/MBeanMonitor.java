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

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.clusterinfo.NodeStatus;

/**
 * Checks the MBean state
 * @author Mark Hoffmann
 * @since 18.03.2022
 */
public class MBeanMonitor implements NodeStatus {

	private final OperatingSystemMXBean os;
	private final MemoryMXBean memory;

	public MBeanMonitor() {
		os = ManagementFactory.getOperatingSystemMXBean();
		memory = ManagementFactory.getMemoryMXBean();
	}

	public Map<String, Object> getMetrics(String... names) {
		Map<String, Object> metrics = new HashMap<>();

		if(fill("availableProcessors", names)){
			metrics.put("availableProcessors", os.getAvailableProcessors());
		}

		if(fill("systemLoadAverage", names)){
			metrics.put("systemLoadAverage", os.getSystemLoadAverage());
		}

		if(fill("heapMemoryUsed", names)){
			metrics.put("heapMemoryUsed", memory.getHeapMemoryUsage().getUsed());
		}

		if(fill("heapMemoryMax", names)){
			metrics.put("heapMemoryMax", memory.getHeapMemoryUsage().getMax());
		}

		if(fill("nonHeapMemoryUsed", names)){
			metrics.put("nonHeapMemoryUsed", memory.getNonHeapMemoryUsage().getUsed());
		}

		if(fill("nonHeapMemoryMax", names)){
			metrics.put("nonHeapMemoryMax", memory.getNonHeapMemoryUsage().getMax());
		}

		return metrics;
	}

	private boolean fill(String key, String... names){
		if(names == null || names.length == 0)
			return true;

		for(String n : names){
			if(key.equals(n)){
				return true;
			}
		}

		return false;
	}
}

