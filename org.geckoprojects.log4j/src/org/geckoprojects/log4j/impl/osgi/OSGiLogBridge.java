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
package org.geckoprojects.log4j.impl.osgi;

import java.util.AbstractMap.SimpleEntry;

import org.apache.logging.log4j.core.LoggerContext;
import org.geckoprojects.log4j.LogManagerProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.admin.LoggerAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Log4J bridge for the OSGi Logger. It tracks all {@link LoggerAdmin} instances and in addition to that
 * the {@link LogReaderService} instances and assigns every instance a {@link Log4JOSGiLogListener} instance.
 * @author Mark Hoffmann
 */
@Component
public class OSGiLogBridge {
	
	@Reference
	private LogManagerProvider provider;
	private volatile ServiceTracker<LoggerAdmin, LogReaderServiceTracker> loggerAdminTracker;
	
	@Activate
	public void activate(BundleContext bundleContext) {
		loggerAdminTracker = new ServiceTracker<LoggerAdmin, LogReaderServiceTracker>(bundleContext, LoggerAdmin.class,
				null) {

			@Override
			public LogReaderServiceTracker addingService(ServiceReference<LoggerAdmin> reference) {
				LoggerAdmin loggerAdmin = bundleContext.getService(reference);
				LogReaderServiceTracker lrst = new LogReaderServiceTracker(bundleContext, loggerAdmin);
				lrst.open();

				return lrst;
			}

			@Override
			public void removedService(ServiceReference<LoggerAdmin> reference, LogReaderServiceTracker lrst) {
				lrst.close();
			}
		};

		loggerAdminTracker.open();
	}
	
	@Deactivate
	public void deactivate() {
		if (loggerAdminTracker != null) {
			loggerAdminTracker.close();
		}
	}
	
	class LogReaderServiceTracker extends ServiceTracker<LogReaderService, Pair> {

		public LogReaderServiceTracker(BundleContext context, LoggerAdmin loggerAdmin) {
			super(context, LogReaderService.class, null);
			this.loggerAdmin = loggerAdmin;
		}

		@Override
		public Pair addingService(ServiceReference<LogReaderService> reference) {
			LogReaderService logReaderService = context.getService(reference);
			if (provider.getContext() instanceof LoggerContext) {
				Log4JOSGiLogListener log4jLogListener = new Log4JOSGiLogListener(loggerAdmin, (LoggerContext) provider.getContext());
				logReaderService.addLogListener(log4jLogListener);
				return new Pair(logReaderService, log4jLogListener);
			}
			return null;
		}

		@Override
		public void removedService(ServiceReference<LogReaderService> reference, Pair pair) {
			if (pair != null) {
				pair.getKey().removeLogListener(pair.getValue());
			}
		}

		private final LoggerAdmin loggerAdmin;

	}
	
	/**
	 * Associate a {@link LogReaderService} with the corresponding registered {@link LogListener} instance
	 */
	static class Pair extends SimpleEntry<LogReaderService, Log4JOSGiLogListener> {

		private static final long serialVersionUID = 1L;

		public Pair(LogReaderService logReaderService, Log4JOSGiLogListener log4JLogListener) {
			super(logReaderService, log4JLogListener);
		}

	}

}
