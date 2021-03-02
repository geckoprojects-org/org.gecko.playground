package org.gecko.playground.wab.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
@Path("")
public class ExampleResource {

	@GET
	@Path("example")
	public String sayHello() {
		return "Hello";
	}

}