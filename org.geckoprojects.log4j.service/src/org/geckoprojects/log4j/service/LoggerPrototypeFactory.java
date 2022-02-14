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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.Logger;
import org.geckoprojects.log4j.LogManagerProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceRegistration;

/**
 * Prototype service factory that handles Log4J {@link Logger}'s
 * @author mark
 *
 */
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

	/**
	 * Sets a new {@link LogManagerProvider} in a safe way. All Logger proxy instance are updated
	 * with the new provider
	 * @param provider the {@link LogManagerProvider} to be set
	 */
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

	/**
	 * Unsets a {@link LogManagerProvider}. All {@link Logger} proxies are updated, to handle the missing 
	 * provider instance
	 * @param provider the {@link LogManagerProvider} to be unset
	 */
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
