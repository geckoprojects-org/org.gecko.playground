package org.gecko.playground.http.jersey.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.osgi.annotation.bundle.Requirement;
import org.osgi.annotation.bundle.Requirements;
import org.osgi.framework.namespace.IdentityNamespace;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({
		ElementType.TYPE, ElementType.PACKAGE
})
@Requirements({
	@Requirement(namespace = IdentityNamespace.IDENTITY_NAMESPACE, name = "org.glassfish.jersey.inject.jersey-hk2", filter = "(osgi.identity=org.glassfish.jersey.inject.jersey-hk2)"),
	@Requirement(namespace = IdentityNamespace.IDENTITY_NAMESPACE, name = "org.glassfish.jersey.media.jersey-media-sse", filter = "(osgi.identity=org.glassfish.jersey.media.jersey-media-sse)"),
	@Requirement(namespace = IdentityNamespace.IDENTITY_NAMESPACE, name = "org.glassfish.jersey.media.jersey-media-jaxb", filter = "(osgi.identity=org.glassfish.jersey.media.jersey-media-jaxb)"),
	@Requirement(namespace = IdentityNamespace.IDENTITY_NAMESPACE, name = "org.glassfish.jersey.containers.jersey-container-servlet", filter = "(osgi.identity=org.glassfish.jersey.containers.jersey-container-servlet)"),
	@Requirement(namespace = IdentityNamespace.IDENTITY_NAMESPACE, name = "jakarta.ws.rs-api", filter = "(osgi.identity=jakarta.ws.rs-api)"),
	@Requirement(namespace = IdentityNamespace.IDENTITY_NAMESPACE, name = "jakarta.activation-api", filter = "(osgi.identity=jakarta.activation-api)"),
	@Requirement(namespace = IdentityNamespace.IDENTITY_NAMESPACE, name = "com.sun.xml.bind.jaxb-impl", filter = "(osgi.identity=com.sun.xml.bind.jaxb-impl)"),
	@Requirement(namespace = IdentityNamespace.IDENTITY_NAMESPACE, name = "jakarta.xml.bind-api", filter = "(osgi.identity=jakarta.xml.bind-api)"),
	@Requirement(namespace = IdentityNamespace.IDENTITY_NAMESPACE, name = "jakarta.validation.jakarta.validation-api", filter = "(osgi.identity=jakarta.validation.jakarta.validation-api)"),
})
public @interface RequireJersey {

}
