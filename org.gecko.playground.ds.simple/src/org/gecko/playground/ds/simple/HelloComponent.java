package org.gecko.playground.ds.simple;

import org.osgi.service.cm.annotations.RequireConfigurationAdmin;
import org.osgi.service.component.annotations.Component;

@Component
public class HelloComponent {
	
	public HelloComponent () {
		System.out.println("HelloComponent created");
	}

}
