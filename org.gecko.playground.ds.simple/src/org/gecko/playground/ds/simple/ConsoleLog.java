package org.gecko.playground.ds.simple;

import org.gecko.playground.log.Log;
import org.osgi.service.component.annotations.Component;

@Component
public class ConsoleLog implements Log {

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

}
