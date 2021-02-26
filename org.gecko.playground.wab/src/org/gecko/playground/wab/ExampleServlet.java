package org.gecko.playground.wab;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.annotation.bundle.Requirement;
import org.osgi.annotation.bundle.Requirements;
import org.osgi.framework.BundleContext;

@Requirements({
	@Requirement(namespace = "osgi.identity", filter = "(osgi.identity=org.apache.aries.spifly.dynamic.framework.extension)"),
	@Requirement(namespace = "osgi.identity", filter = "(osgi.identity=org.apache.felix.http.jetty)")
})
public class ExampleServlet extends HttpServlet {
	 
	private static final long serialVersionUID = 1L;
	private String message;

	   public void init() throws ServletException {
	      message = "Hello World";
	   }

	   public void doGet(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException {
		   BundleContext ctxt = (BundleContext) 
				    request.getServletContext().getAttribute("osgi-bundlecontext");
	      // Set response content type
	      response.setContentType("text/html");

	      // Actual logic goes here.
	      PrintWriter out = response.getWriter();
	      out.println("<h1>" + message + "</h1>");
	      out.println("<h1>" + ctxt.getBundle(0).getSymbolicName() + "</h1>");
	   }

	   public void destroy() {
	      // do nothing.
	   }
	}
