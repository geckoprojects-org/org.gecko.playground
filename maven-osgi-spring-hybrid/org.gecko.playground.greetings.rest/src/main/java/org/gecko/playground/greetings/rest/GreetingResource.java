package org.gecko.playground.greetings.rest;


import org.gecko.playground.greetings.api.GreetingsService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jakartars.whiteboard.propertytypes.JakartarsResource;
import org.springframework.stereotype.Component;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * One resource class for both runtimes:
 * <ul>
 * <li>Spring creates it as a bean and injects the service through the single
 * constructor (no {@code @Autowired} needed).</li>
 * <li>In OSGi, DS creates it and injects the service through the same
 * constructor ({@code @Activate} + {@code @Reference}). The Jakarta REST
 * whiteboard picks it up via {@code @JakartarsResource}.</li>
 * </ul>
 * The DS annotations have class retention only, so Spring never sees them. In
 * OSGi, the Spring stereotype annotation comes from the
 * {@code greetings.spring.context} bundle.
 */
@Path("/")
@Component
@org.osgi.service.component.annotations.Component(service = GreetingResource.class, scope = ServiceScope.PROTOTYPE)
@JakartarsResource
public class GreetingResource {

	private final GreetingsService service;

	@Activate
	public GreetingResource(@Reference GreetingsService service) {
		this.service = service;
	}

	@Path("/greeting")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response greeting(@DefaultValue("World") @QueryParam(value = "name") String name) {
		return Response.ok(service.greeting(name)).build();
	}
}
