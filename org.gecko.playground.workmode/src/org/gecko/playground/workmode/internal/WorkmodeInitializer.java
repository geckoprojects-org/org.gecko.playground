package org.gecko.playground.workmode.internal;

import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

import org.gecko.playground.Namespaces;
import org.gecko.playground.configuration.PlaygroundCoreConfiguration;
import org.gecko.playground.workmode.Workmode;
import org.gecko.playground.workmode.WorkmodeConstants;
import org.osgi.annotation.bundle.Capability;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.condition.Condition;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

@Capability(namespace = "osgi.service", name = "Workmode Condition", attribute = {"objectClass=org.osgi.service.condition.Condition"})
@Capability(namespace = WorkmodeConstants.CAPABILITY_WORKMODE, name = WorkmodeConstants.CAPABILITY_WORKMODE_NAME, version = "1.0")
@Component(immediate = true, scope = ServiceScope.SINGLETON, service = WorkmodeInitializer.class, configurationPolicy = ConfigurationPolicy.REQUIRE, configurationPid = Namespaces.CONFIGURATION_CORE)
public class WorkmodeInitializer {
	
	@Reference(service = LoggerFactory.class)
	private Logger logger;
	private volatile Workmode currentWorkmode = Workmode.OFFLINE;
	private ServiceRegistration<Condition> workModeRegistration = null;
	
	@Activate
	public void activate(PlaygroundCoreConfiguration config, BundleContext context) {
		Date create = new Date();
		Dictionary<String,Object> properties = getConditionProperties(config, create.getTime());
		workModeRegistration = context.registerService(Condition.class, Condition.INSTANCE, properties);
		logger.info("Initialized workmode '{}' at {}", currentWorkmode, create.toString());
	}
	
	@Modified
	public void modified(PlaygroundCoreConfiguration config, BundleContext context) {
		if (workModeRegistration != null) {
			Date update = new Date();
			Workmode oldWorkMode = currentWorkmode;
			Dictionary<String,Object> properties = getConditionProperties(config, update.getTime());
			workModeRegistration.setProperties(properties);
			logger.info("Updated workmode from '{}' to '{}' at {}", oldWorkMode, currentWorkmode, update.toString());
		} else {
			activate(config, context);
		}
	}
	
	@Deactivate
	public void deactivate() {
		if (workModeRegistration != null) {
			workModeRegistration.unregister();
		}
	}
	
	private Dictionary<String, Object> getConditionProperties(PlaygroundCoreConfiguration configuration, long timestamp) {
		Workmode mode = Workmode.valueOf(configuration.workmode());
		if (mode == null) {
			logger.warn("Unknown work mode '{}', defaulting to OFFLINE", configuration.workmode());
			currentWorkmode = Workmode.OFFLINE;
		} else {
			currentWorkmode = mode;
		}
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(Condition.CONDITION_ID, WorkmodeConstants.CONDITION_WORKMODE);
		properties.put(WorkmodeConstants.CONDITION_WORKMODE, currentWorkmode);
		properties.put(WorkmodeConstants.CONDITION_WORKMODE_TIME, timestamp);
		return properties;
	}


}
