package org.gecko.playground.ds.simple.properties;

import org.gecko.playground.ds.simple.logging.Log;
import org.osgi.service.component.annotations.Component;

//@Component(property = "fizz=buzz")
@MyServiceProp(foo = "bar")
public class AnotherLog implements Log {

	@Override
	public void logMessage(String message) {
		System.out.println("ANOTHER LOG : " + message);
	}

}
