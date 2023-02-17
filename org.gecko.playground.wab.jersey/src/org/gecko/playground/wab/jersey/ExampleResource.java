package org.gecko.playground.wab.jersey;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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