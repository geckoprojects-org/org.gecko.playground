package org.gecko.playground.rest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jakartars.whiteboard.propertytypes.JakartarsResource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Component(service=ExampleResource.class, scope = ServiceScope.PROTOTYPE)
@JakartarsResource
@Path("")
public class ExampleResource {

	@GET
	@Path("example")
	public String sayHello() {
		return "Hello out there";
	}

}