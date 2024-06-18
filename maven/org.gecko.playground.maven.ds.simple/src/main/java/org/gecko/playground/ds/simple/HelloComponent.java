package org.gecko.playground.ds.simple;

import static java.util.Objects.isNull;

import org.osgi.service.component.annotations.Component;

@Component
public class HelloComponent {
	
	public HelloComponent () {
		String property = System.getProperty("test.properties");
		if (isNull(property)) {
			property = "<no-propery-provided>";
		}
		System.out.println("HelloComponent created with property: " + property);
	}

}
