package org.gecko.playground.wab.jersey;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;

@Path("events")
public class SseResource {
 
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void getServerSentEvents(@Context SseEventSink eventSink, @Context Sse sse) {
    	System.out.println("SSE Server " + sse + ", " + eventSink);
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                // ... code that waits 1 second
                final OutboundSseEvent event = sse.newEventBuilder()
                    .name("message-to-client")
                    .data(String.class, "Hello world " + i + "!")
                    .build();
                eventSink.send(event);
            }
        }).start();
    }
    
    @GET
    @Produces("text/plain")
    public String getStatustest() {
        return "sse active";
    }
}