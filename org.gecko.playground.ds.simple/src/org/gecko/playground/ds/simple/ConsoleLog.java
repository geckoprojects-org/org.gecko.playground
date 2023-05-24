package org.gecko.playground.ds.simple;

import org.gecko.playground.ds.simple.logging.Log;
import org.osgi.service.component.annotations.Component;

@Component
public class ConsoleLog implements Log {

	public ConsoleLog () {
		System.out.println("ConsoleLog created");
	}

	@Override
	public void logMessage(String message) {
		System.out.println("LOG : " + message);
	}

	@Override
	public String info(String message) {
		return "INFO: " + message;
	}

}
