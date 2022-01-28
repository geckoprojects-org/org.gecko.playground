package org.gecko.log4.extension;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.Provider;
import org.gecko.log4.extension.api.LogManagerProvider;
import org.osgi.annotation.bundle.Header;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

@Header(name=Constants.BUNDLE_ACTIVATOR, value="${@class}")
public class Activator implements BundleActivator {
	
	private ServiceRegistration<?> loggerService;
	private ServiceRegistration<LogManagerProvider> logManagerProvider;
	private org.apache.logging.log4j.core.osgi.Activator activator = new org.apache.logging.log4j.core.osgi.Activator();

	@Override
	public void start(BundleContext context) throws Exception {
		activator.start(context);
		ServiceReference<Provider> serviceReference = context.getServiceReference(Provider.class);
		Provider provider = context.getService(serviceReference);
		LogManagerProvider logManager = new LogManager(provider);
		LoggerPrototypeFactory loggerFactory = new LoggerPrototypeFactory(logManager);
		logManagerProvider = context.registerService(LogManagerProvider.class, logManager, null);
		loggerService = context.registerService(Logger.class, loggerFactory, null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (loggerService != null) {
			loggerService.unregister();
		}
		if (logManagerProvider != null) {
			logManagerProvider.unregister();
		}
		activator.stop(context);
	}

}
