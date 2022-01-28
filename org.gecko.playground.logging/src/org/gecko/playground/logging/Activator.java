package org.gecko.playground.logging;


import java.util.AbstractMap.SimpleEntry;

import org.gecko.playground.Namespaces;
import org.gecko.playground.logging.impl.Log4JOSGiLogListener;
import org.osgi.annotation.bundle.Capability;
import org.osgi.annotation.bundle.Requirement;
import org.osgi.annotation.bundle.Requirements;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.admin.LoggerAdmin;
import org.osgi.util.tracker.ServiceTracker;


/**
 * We define a capability for the system, that says for the cabability 'ruvconn.logging', I have the name 'log4j' in version 2.0
 * In addition to that, we create a requirement wiring to the log4j bundles outside the scope of a require bundle.
 * We don't really need the packages to make our code compile. Instead, we need the log4j implementations.
 * At last we also need a requirement for a configuration 
 * @author xv884az
 *
 */
//@Requirement(namespace = "osgi.identity", name = "org.gecko.log4.extension", filter = "(&(version>=2.13)(!(version>=3.0)))"),
@Capability(namespace = Namespaces.CAPABILITY_LOGGING, name = Namespaces.LOGGING_LOG4J, version = "2.0")
@Requirements({
	@Requirement(namespace = "osgi.identity", name = "org.apache.logging.log4j.api", filter = "(&(version>=2.13)(!(version>=3.0)))"),
	@Requirement(namespace = "osgi.identity", name = "org.apache.logging.log4j.core", filter = "(&(version>=2.13)(!(version>=3.0)))"),
	@Requirement(namespace = "osgi.identity", name = "org.apache.logging.log4j.slf4j-impl", filter = "(&(version>=2.13)(!(version>=3.0)))"),
	@Requirement(namespace = Namespaces.CAPABILITY_LOGGING, name = Namespaces.LOGGING_CONFIG, version="2.0", filter="(stage=*)")
})
public class Activator implements BundleActivator {

	private volatile ServiceTracker<LoggerAdmin, LogReaderServiceTracker> loggerAdminTracker;

	@Override
	public void start(BundleContext bundleContext) throws Exception {

		/*
		 * We cannot use the JULBridge, because Riena is doing something
		 * that prevents the Client Monitoring from working
		 */
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

	@Override
	public void stop(BundleContext context) throws Exception {
		loggerAdminTracker.close();
	}

	class LogReaderServiceTracker extends ServiceTracker<LogReaderService, Pair> {

		public LogReaderServiceTracker(BundleContext context, LoggerAdmin loggerAdmin) {
			super(context, LogReaderService.class, null);
			this.loggerAdmin = loggerAdmin;
		}

		@Override
		public Pair addingService(ServiceReference<LogReaderService> reference) {
			LogReaderService logReaderService = context.getService(reference);
			Log4JOSGiLogListener log4jLogListener = new Log4JOSGiLogListener(loggerAdmin);

			logReaderService.addLogListener(log4jLogListener);
			return new Pair(logReaderService, log4jLogListener);
		}

		@Override
		public void removedService(ServiceReference<LogReaderService> reference, Pair pair) {
			pair.getKey().removeLogListener(pair.getValue());
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

