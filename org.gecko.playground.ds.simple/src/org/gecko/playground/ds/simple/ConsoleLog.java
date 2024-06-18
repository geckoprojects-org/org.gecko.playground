package org.gecko.playground.ds.simple;

import org.gecko.playground.log.Log;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.annotations.RequireConfigurationAdmin;

@Component(immediate = true)
@RequireConfigurationAdmin
public class ConsoleLog implements Log {
	
	private ConfigurationAdmin ca;

	public ConsoleLog () {
		System.out.println("ConsoleLog created");
	}

	public ConsoleLog (String source) {
		System.out.println("ConsoleLog created by " + source);
	}
	

	@Override
	public void logMessage(String message) {
		System.out.println("LOG : " + message);
	}

	@Override
	public String info(String message) {
		return "INFO: " + message;
	}

	/**
	 * Returns the ca.
	 * @return the ca
	 */
	public ConfigurationAdmin getCa() {
		return ca;
	}

	/**
	 * Sets the ca.
	 * @param ca the ca to set
	 */
	@Reference(unbind = "removeMyConfig")
	public void setCa(ConfigurationAdmin ca) {
		this.ca = ca;
	}

}
