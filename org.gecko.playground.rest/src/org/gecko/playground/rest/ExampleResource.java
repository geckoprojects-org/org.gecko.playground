package org.gecko.playground.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.osgi.service.component.annotations.*;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@Component(service=ExampleResource.class, scope = ServiceScope.PROTOTYPE)
@JaxrsResource
@Path("")
public class ExampleResource {

	@GET
	@Path("example")
	public String sayHello() {
		return "Hello";
	}

}