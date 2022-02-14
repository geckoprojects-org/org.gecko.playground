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
package org.geckoprojects.log4j.impl.jul;

import java.util.logging.Handler;
import java.util.logging.Logger;

import org.apache.logging.log4j.Level;
import org.geckoprojects.log4j.LogManagerProvider;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Component that handles bridging JUL log records. The component tracks the lifecycle of Log4J
 * and initializes or deactivates the bridge. It also tracks Log4J's default log level.
 * @author Mark Hoffmann
 *
 */
@Component
public class JULLogBridge {
	
	@Reference
	private LogManagerProvider provider;
	
	@Activate
	public void activate() {
		Level rootLevel = provider.getLogger().getLevel();
		Logger rootLogger = getRootLogger();
		if (!Level.INFO.equals(rootLevel)) {
			rootLogger.setLevel(getJULLogLevel(rootLevel));
		}
		rootLogger.addHandler(new Log4JJULBridgeHandler());
	}
	
	@Deactivate
	public void deactivate() {
		java.util.logging.Logger rootLogger = getRootLogger();
		Handler[] handlers = rootLogger.getHandlers();
		for (int i = 0; i < handlers.length; i++) {
			if (handlers[i] instanceof Log4JJULBridgeHandler) {
				rootLogger.removeHandler(handlers[i]);
			}
		}
	}
	
	private static java.util.logging.Logger getRootLogger() {
		return java.util.logging.LogManager.getLogManager().getLogger(""); //$NON-NLS-1$
	}
	
	private java.util.logging.Level getJULLogLevel(Level log4JLevel) {
		if (Level.DEBUG.equals(log4JLevel)) {
			return java.util.logging.Level.FINE;
		} else if (Level.TRACE.equals(log4JLevel)) {
			return java.util.logging.Level.FINEST;
		} else if (Level.WARN.equals(log4JLevel)) {
			return java.util.logging.Level.WARNING;
		} else if (Level.INFO.equals(log4JLevel)) {
			return java.util.logging.Level.INFO;
		}
		return java.util.logging.Level.SEVERE;
	}

}
