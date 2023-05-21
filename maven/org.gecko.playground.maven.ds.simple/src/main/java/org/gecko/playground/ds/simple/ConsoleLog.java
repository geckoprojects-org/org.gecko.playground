package org.gecko.playground.ds.simple;

import org.gecko.playground.ds.simple.logging.Log;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true)
public class ConsoleLog implements Log {

	public ConsoleLog () {
		System.out.println("ConsoleLog created");
	}

	@Override
	public void logMessage(String message) {
		System.out.println("LOG : " + message);
	}

}
