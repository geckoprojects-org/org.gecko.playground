package org.gecko.playground.wab.jersey;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
@Path("")
public class ExampleResource {

	@GET
	@Path("example")
	public String sayHello() {
		return "Hello";
	}
	
	@GET
	@Path("cs/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public CompletionStage<String> echoCompletionStage(
			@PathParam("name") String value) {

		CompletableFuture<String> cf = new CompletableFuture<>();

		new Thread(() -> {
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				cf.completeExceptionally(e);
				return;
			}
			cf.complete(value);
		}).start();

		return cf;
	}

}