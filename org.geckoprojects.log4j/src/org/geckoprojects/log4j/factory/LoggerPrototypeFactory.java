package org.geckoprojects.log4j.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.Logger;
import org.geckoprojects.log4j.api.LogManagerProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceRegistration;

public class LoggerPrototypeFactory implements PrototypeServiceFactory<Logger>{

	private final Map<Bundle, LoggerProxy> proxyMap = new ConcurrentHashMap<Bundle, LoggerProxy>();
	private AtomicReference<LogManagerProvider> logManagerRef = new AtomicReference<LogManagerProvider>();
	private AtomicReference<LogManagerContext> logManagerContextRef = new AtomicReference<LogManagerContext>();

	public LoggerPrototypeFactory() {
	}

	@Override
	public Logger getService(Bundle bundle, ServiceRegistration<Logger> registration) {
		try {
			LoggerProxy loggerProxy = new LoggerProxy(registration, bundle, logManagerContextRef.get());
			proxyMap.put(bundle, loggerProxy);
			return loggerProxy.getLoggerProxy();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void ungetService(Bundle bundle, ServiceRegistration<Logger> registration, Logger service) {
		LoggerProxy loggerProxy = proxyMap.remove(bundle);
		if (loggerProxy != null) {
			loggerProxy.dispose();
		}
	}

	public LogManagerProvider getLogManagerProvider() {
		return logManagerRef.get();
	}

	public void setLogManagerProvider(LogManagerProvider provider) {
		if (provider == null) {
			throw new IllegalStateException("LogManagerProvider must not be null");
		}
		LogManagerProvider current = logManagerRef.get();
		if (!provider.equals(current)) {
			logManagerRef.set(provider);
			logManagerContextRef.set(new LogManagerContext(provider));
			synchronized (proxyMap) {
				proxyMap.values().forEach((p)->p.setContext(logManagerContextRef.get()));
			}
		}
	}

	public void unsetLogManagerProvider(LogManagerProvider provider) {
		if (provider == null) {
			throw new IllegalStateException("LogManagerProvider must not be null");
		}
		LogManagerProvider current = logManagerRef.get();
		if (provider.equals(current)) {
			synchronized (proxyMap) {
				proxyMap.values().forEach((p)->p.unsetContext(logManagerContextRef.get()));
			}
			logManagerContextRef.set(null);
			logManagerRef.set(null);
		}
	}

}
