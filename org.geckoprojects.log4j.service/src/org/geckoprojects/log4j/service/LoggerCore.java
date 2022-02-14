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
package org.geckoprojects.log4j.service;

import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.Provider;
import org.geckoprojects.log4j.LogManagerProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * Component that tracks the changes of {@link LogManagerProvider} services.
 * It registers a {@link LoggerPrototypeFactory} that is able to create {@link Logger} instance as 
 * service that are agnostic against changes of the {@link Provider}. So the Log4J Core bundles can switched 
 * during runtime
 * @author Mark Hoffmann
 *
 */
@Component
public class LoggerCore {
	
	private LogManagerProvider logManager;
	private ServiceRegistration<Logger> loggerFactoryService;
	private final AtomicReference<LoggerPrototypeFactory> prototypeFactory = new AtomicReference<LoggerPrototypeFactory>();
	private BundleContext context;
	
	@Activate
	public void activate(BundleContext context) {
		this.context = context;
		if (loggerFactoryService == null && context != null) {
			loggerFactoryService = context.registerService(Logger.class, prototypeFactory.get(), null);
		}
	}
	
	@Deactivate
	public void deactivate() {
		if (loggerFactoryService != null) {
			loggerFactoryService.unregister();
		}
	}
	
	@Reference(cardinality = ReferenceCardinality.OPTIONAL, policy = ReferencePolicy.DYNAMIC, unbind = "unsetLogManager")
	public void setLogManager(LogManagerProvider manager) {
		logManager = manager;
		prototypeFactory.compareAndSet(null, new LoggerPrototypeFactory());
		prototypeFactory.get().setLogManagerProvider(logManager);
		if (loggerFactoryService == null && context != null) {
			loggerFactoryService = context.registerService(Logger.class, prototypeFactory.get(), null);
		}
		
	}
	
	public void unsetLogManager(LogManagerProvider manager) {
		prototypeFactory.get().unsetLogManagerProvider(logManager);
	}

}
