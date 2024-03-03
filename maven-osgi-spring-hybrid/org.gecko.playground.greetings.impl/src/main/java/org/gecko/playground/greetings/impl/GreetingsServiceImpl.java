package org.gecko.playground.greetings.impl;

import java.util.concurrent.atomic.AtomicLong;

import org.gecko.playground.greetings.api.Greeting;
import org.gecko.playground.greetings.api.GreetingsService;
import org.springframework.stereotype.Component;

@Component
@org.osgi.service.component.annotations.Component
public class GreetingsServiceImpl implements GreetingsService {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@Override
	public Greeting greeting(String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

}
