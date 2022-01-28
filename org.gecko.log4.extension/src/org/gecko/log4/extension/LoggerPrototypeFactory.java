package org.gecko.log4.extension;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.gecko.log4.extension.api.LogManagerProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;

public class LoggerPrototypeFactory implements PrototypeServiceFactory<Logger> {
	
	private volatile Map<Bundle, LoggerContext> contextMap = new ConcurrentHashMap<>();
	private final LogManagerProvider logManager;
	
	public LoggerPrototypeFactory(LogManagerProvider logManager) {
		this.logManager = logManager;
	}

	@Override
	public Logger getService(Bundle bundle, ServiceRegistration<Logger> registration) {
		BundleWiring wiring = bundle.adapt(BundleWiring.class);
		LoggerContext context = (LoggerContext) logManager.getContext(wiring.getClassLoader(), false);
		synchronized (contextMap) {
			contextMap.put(bundle, context);
		}
		return context.getLogger(bundle.getSymbolicName());
	}

	@Override
	public void ungetService(Bundle bundle, ServiceRegistration<Logger> registration, Logger service) {
		LoggerContext context = null;
		synchronized (contextMap) {
			context = contextMap.remove(bundle);
		}
		if (context == null) {
			return;
		}
		logManager.shutdown(context);
	}

}
