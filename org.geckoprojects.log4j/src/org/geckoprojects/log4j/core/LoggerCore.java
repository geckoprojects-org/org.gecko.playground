package org.geckoprojects.log4j.core;

import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.Logger;
import org.geckoprojects.log4j.api.LogManagerProvider;
import org.geckoprojects.log4j.factory.LoggerPrototypeFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

@Component
public class LoggerCore {
	
	private LogManagerProvider logManager;
	private ServiceRegistration<Logger> loggerFactoryService;
	private final AtomicReference<LoggerPrototypeFactory> prototypeFactory = new AtomicReference<LoggerPrototypeFactory>();
	private BundleContext context;
	
	@Activate
	public void activate(BundleContext context) {
		this.context = context;
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
		if (loggerFactoryService == null) {
			loggerFactoryService = context.registerService(Logger.class, prototypeFactory.get(), null);
		}
		
	}
	
	public void unsetLogManager(LogManagerProvider manager) {
		prototypeFactory.get().unsetLogManagerProvider(logManager);
	}

}
