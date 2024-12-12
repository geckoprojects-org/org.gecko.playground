package org.gecko.playground.ds.filter;

import org.osgi.service.component.annotations.Component;

@Component(property = "lang=ES")
public class HolaImpl implements GreeterService {

	@Override
	public String greet(String name) {
		return "Hola, " + name;
	}

}
