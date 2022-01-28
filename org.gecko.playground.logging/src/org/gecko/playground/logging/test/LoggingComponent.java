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
package org.gecko.playground.logging.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author mark
 * @since 28.01.2022
 */
@Component
public class LoggingComponent {

	Logger log4JLogger = LogManager.getLogger();
	org.slf4j.Logger slf4JLogger = LoggerFactory.getLogger(LoggingComponent.class);
	@Reference(service=org.osgi.service.log.LoggerFactory.class)
	org.osgi.service.log.Logger osgiLogger;
	
	@Activate
	public void activate() {
		System.out.println("Activate");
		log4JLogger.info("Activate with Log4J");
		slf4JLogger.info("Activate with SLF4J");
		osgiLogger.info("Activate with OSGi Logging");
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("Deactivate");
		log4JLogger.info("Deactivate with Log4J");
		slf4JLogger.info("Deactivate with SLF4J");
		osgiLogger.info("Deactivate with OSGi Logging");
	}
	
}
