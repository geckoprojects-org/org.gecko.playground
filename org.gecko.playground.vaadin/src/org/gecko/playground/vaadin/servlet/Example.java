package org.gecko.playground.vaadin.servlet;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.gecko.playground.vaadin.RequireVaadin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.*;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

import com.vaadin.flow.function.DeploymentConfiguration;
import com.vaadin.flow.server.VaadinServlet;

@RequireVaadin
@Component(immediate = true)
public class Example {
	
	private ServiceRegistration<Servlet> servletRegistration;

	private static class FixedVaadinServlet extends VaadinServlet {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void init(ServletConfig servletConfig) throws ServletException {
			super.init(servletConfig);
			getService().setClassLoader(getClass().getClassLoader());
		}
		
		@Override
		protected DeploymentConfiguration createDeploymentConfiguration(Properties initParameters) {
			initParameters.setProperty("compatibilityMode", "true");
			return super.createDeploymentConfiguration(initParameters);
		}
	}

	@Activate
	public void activate(BundleContext ctx) {
		Dictionary<String, Object> properties = new Hashtable<String, Object>();
		properties.put(HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_ASYNC_SUPPORTED, true);
		properties.put(HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "/*");
		servletRegistration = ctx.registerService(Servlet.class, new FixedVaadinServlet(), properties);
	}
	
	@Deactivate
	public void deactivate() {
		if(servletRegistration != null) {
			servletRegistration.unregister();
		}
	}

}
