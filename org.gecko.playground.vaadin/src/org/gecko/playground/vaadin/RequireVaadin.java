package org.gecko.playground.vaadin;

import org.osgi.annotation.bundle.Requirement;
import org.osgi.annotation.bundle.Requirements;
import org.osgi.service.http.whiteboard.annotations.RequireHttpWhiteboard;

@RequireHttpWhiteboard
@Requirements({
	@Requirement(namespace = "osgi.identity", filter = "(osgi.identity=com.vaadin.flow.server)"),
	@Requirement(namespace = "osgi.identity", filter = "(osgi.identity=com.vaadin.flow.client)"),
	@Requirement(namespace = "osgi.identity", filter = "(osgi.identity=com.vaadin.flow.osgi)"),
	@Requirement(namespace = "osgi.identity", filter = "(osgi.identity=com.vaadin.flow.data)"),
	@Requirement(namespace = "osgi.identity", filter = "(osgi.identity=com.vaadin.flow.push)"),
	@Requirement(namespace = "osgi.identity", filter = "(osgi.identity=com.vaadin.flow.html.components)")
})
public @interface RequireVaadin {

}
