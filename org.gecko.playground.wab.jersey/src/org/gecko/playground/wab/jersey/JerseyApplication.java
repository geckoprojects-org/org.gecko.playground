package org.gecko.playground.wab.jersey;

import java.util.HashSet;
import java.util.Set;

import jakarta.ws.rs.core.Application;

import org.gecko.playground.http.jersey.annotations.RequireJersey;

@RequireJersey
public class JerseyApplication extends Application {
	
	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<Object>();
		return singletons;
	}

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> result = new HashSet<Class<?>>();
        result.add(ExampleResource.class);
        result.add(StatusResource.class);
        result.add(SseResource.class);
        return result;
    }
    

}