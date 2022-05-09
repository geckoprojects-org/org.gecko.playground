package org.gecko.playground.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.annotations.RequireJaxrsWhiteboard;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@RequireJaxrsWhiteboard
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