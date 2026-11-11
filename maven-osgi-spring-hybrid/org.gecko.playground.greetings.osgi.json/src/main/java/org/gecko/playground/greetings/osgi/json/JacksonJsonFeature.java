package org.gecko.playground.greetings.osgi.json;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jakartars.whiteboard.propertytypes.JakartarsExtension;
import org.osgi.service.jakartars.whiteboard.propertytypes.JakartarsName;

import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;

import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;

/**
 * Registers Jackson as the JSON provider with the Jakarta REST whiteboard.
 * <p>
 * Spring Boot configures Jackson for Jersey automatically. In OSGi, the
 * whiteboard only uses extensions that are registered as services, so this
 * component does that job for the OSGi application.
 * <p>
 * It registers the provider <em>class</em> rather than a provider instance:
 * Jersey then creates a fresh provider, including its {@code @Context}
 * injections, every time the whiteboard (re)builds an application.
 */
@Component(service = Feature.class)
@JakartarsExtension
@JakartarsName("jackson-json")
public class JacksonJsonFeature implements Feature {

	@Override
	public boolean configure(FeatureContext context) {
		context.register(JacksonJsonProvider.class);
		return true;
	}
}
