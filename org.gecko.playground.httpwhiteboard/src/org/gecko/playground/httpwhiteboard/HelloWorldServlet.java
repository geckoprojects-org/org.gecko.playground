package org.gecko.playground.httpwhiteboard;

import java.io.IOException;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.servlet.whiteboard.annotations.RequireHttpWhiteboard;

@Component(service = Servlet.class, 
           scope=ServiceScope.PROTOTYPE,
           property= {"osgi.http.whiteboard.servlet.pattern=/hello", "osgi.http.whiteboard.context.name=hello"})
@RequireHttpWhiteboard
public class HelloWorldServlet extends jakarta.servlet.http.HttpServlet {
 
    private static final long serialVersionUID = -5661065509033392438L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        resp.getWriter().println("Hello World");
    }
}