package org.gecko.playground.greetings.rest;


import org.gecko.playground.greetings.api.GreetingsService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jakartars.whiteboard.propertytypes.JakartarsResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@Component
@org.osgi.service.component.annotations.Component(service = GreetingResource.class, scope = ServiceScope.PROTOTYPE)
@JakartarsResource
public class GreetingResource {

	@Autowired 
	@Reference
	GreetingsService service;
	
	@Path("/greeting")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response greeting(@DefaultValue("World") @QueryParam(value = "name") String name) {
		return Response.ok(service.greeting(name)).build();
	}
}
