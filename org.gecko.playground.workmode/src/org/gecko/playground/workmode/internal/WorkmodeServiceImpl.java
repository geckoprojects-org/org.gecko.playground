package org.gecko.playground.workmode.internal;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.gecko.playground.Namespaces;
import org.gecko.playground.workmode.WorkmodeConstants;
import org.gecko.playground.workmode.Workmode;
import org.gecko.playground.workmode.WorkmodeService;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

@Component(service = WorkmodeService.class)
public class WorkmodeServiceImpl implements WorkmodeService {
	
	@Reference(service = LoggerFactory.class)
	private Logger logger;
	@Reference
	private ConfigurationAdmin configadmin;

	@Override
	public Workmode getWorkmode() {
		Configuration configuration;
		try {
			configuration = configadmin.getConfiguration(Namespaces.CONFIGURATION_CORE);
			Dictionary<String,Object> properties = configuration.getProperties();
			if (properties != null) {
				String mode = (String) properties.get(WorkmodeConstants.CONFIG_PROP_WORKMODE);
				try {
					return Workmode.valueOf(mode);
				} catch (Exception e) {
					throw new IllegalStateException("Received invalid work mode '" + mode + "'", e);
				}
			} else {
				throw new IllegalStateException("No work mode configuration found  for '" + Namespaces.CONFIGURATION_CORE + "'");
			}
		} catch (IOException e) {
			throw new IllegalStateException("Error loading work mode configuration for '" + Namespaces.CONFIGURATION_CORE + "'", e);
		}
	}

	@Override
	public void setWorkmode(Workmode workmode) {
		if (workmode == null) {
			logger.warn("Work mode parameter must not be null.");
			return;
		}
		Configuration configuration;
		try {
			configuration = configadmin.getConfiguration(Namespaces.CONFIGURATION_CORE);
			Dictionary<String,Object> properties = configuration.getProperties();
			if (properties != null) {
				Dictionary<String, Object> newProperties = new Hashtable<String, Object>();
				newProperties.put(WorkmodeConstants.CONFIG_PROP_WORKMODE, workmode.name());
				configuration.updateIfDifferent(newProperties);
			} else {
				logger.warn("There is no work mode configuration for '{}'", Namespaces.CONFIGURATION_CORE);
				throw new IllegalStateException("There is no work mode configuration.");
			}
		} catch (IOException e) {
			logger.error("Error loading work mode configuration '{}'", Namespaces.CONFIGURATION_CORE, e);
			throw new IllegalStateException("Error loading work mode configuration", e);
		} catch (Exception e) {
			logger.error("Error setting work mode '{}'", workmode, e);
			throw new IllegalStateException("Error setting work mode '" + workmode + "'", e);
		}
	}

}
