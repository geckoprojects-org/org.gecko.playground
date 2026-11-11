package org.gecko.playground.greetings.spring.application;

import org.gecko.playground.greetings.rest.GreetingResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig 
{
	public JerseyConfig() 
	{
		register(GreetingResource.class);
	}
}