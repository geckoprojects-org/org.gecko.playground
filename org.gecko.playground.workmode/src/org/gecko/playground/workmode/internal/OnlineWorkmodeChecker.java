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
package org.gecko.playground.workmode.internal;

import java.util.Map;

import org.gecko.playground.workmode.Workmode;
import org.gecko.playground.workmode.WorkmodeService;
import org.osgi.framework.Constants;
import org.osgi.service.clusterinfo.FrameworkNodeStatus;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

/**
 * 
 * @author mark
 * @since 18.03.2022
 */
@Component(immediate = true)
public class OnlineWorkmodeChecker {
	
	@Reference(service = LoggerFactory.class)
	private Logger logger;
	@Reference
	private WorkmodeService workmodeService;
	
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC, target = "(&(osgi.clusterinfo.tags=server)(osgi.clusterinfo.cluster=playground))")
	public void addClusterInfo(FrameworkNodeStatus clusterInfo, Map<String, Object> properties) {
		String text = String.format("Connected to remote framework '%s' with version '%s' from vendor '%s'", properties.get(Constants.FRAMEWORK_UUID), properties.get(Constants.FRAMEWORK_VERSION), properties.get("osgi.clusterinfo.vendor"));
		System.out.println(text);
		System.out.println("System Load: " + clusterInfo.getMetrics("systemLoadAverage").values());
		workmodeService.setWorkmode(Workmode.ONLINE);
	}
	
	public void removeClusterInfo(Map<String, Object> properties) {
		String text = String.format("Disconnected from remote framework '%s' with version '%s' from vendor '%s'", properties.get(Constants.FRAMEWORK_UUID), properties.get(Constants.FRAMEWORK_VERSION), properties.get("osgi.clusterinfo.vendor"));
		System.out.println(text);
		workmodeService.setWorkmode(Workmode.OFFLINE);
	}

}
