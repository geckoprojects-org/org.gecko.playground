package org.geckoprojects.log4j.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.geckoprojects.log4j.api.LogManagerProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;

public class LogManagerContext {

	private volatile Map<Bundle, LoggerContext> contextMap;
	private final LogManagerProvider provider;

	public LogManagerContext(LogManagerProvider provider) {
		this.provider = provider;
		contextMap = new ConcurrentHashMap<>();
	}

	public Logger getLogger(Bundle bundle) {
		return createContext(bundle).getLogger(bundle.getSymbolicName());
	}
	
	public LoggerContext createContext(Bundle bundle) {
		BundleWiring wiring = bundle.adapt(BundleWiring.class);
		LoggerContext context = (LoggerContext) provider.getContext(wiring.getClassLoader(), false);
		synchronized (contextMap) {
			contextMap.put(bundle, context);
		}
		return context;
	}

	public LogManagerProvider getProvider() {
		return provider;
	}

	public void shutdownContext() {
		synchronized (contextMap) {
			contextMap.keySet().forEach(this::shutdownContext);
		}

	}

	public void shutdownContext(Bundle bundle) {
		LoggerContext context = null;
		synchronized (contextMap) {
			context = contextMap.remove(bundle);
		}
		if (context == null) {
			return;
		}
		provider.shutdown(context);
	}
}