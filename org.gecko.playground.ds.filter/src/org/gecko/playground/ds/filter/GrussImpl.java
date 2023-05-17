package org.gecko.playground.ds.filter;

import org.osgi.service.component.annotations.Component;

@Component(property = "lang=RE")
public class GrussImpl implements GreeterService {

	@Override
	public String greet(String name) {
		return "Grüße dich, " + name;
	}

}
