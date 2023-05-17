package org.gecko.playground.ds.filter;

import org.osgi.service.component.annotations.Component;

@Component(property = "lang=EN")
public class GreetImpl implements GreeterService {

	@Override
	public String greet(String name) {
		return "Greetz, " + name;
	}

}
