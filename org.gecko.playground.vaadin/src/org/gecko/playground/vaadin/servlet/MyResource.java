package org.gecko.playground.vaadin.servlet;

import org.osgi.service.component.annotations.Component;

import com.vaadin.flow.osgi.support.OsgiVaadinStaticResource;

@Component
public class MyResource implements OsgiVaadinStaticResource {

	@Override
	public String getPath() {
		return "/resources/index.html";
	}

	@Override
	public String getAlias() {
		return "/frontend/index.html";
	}

}
