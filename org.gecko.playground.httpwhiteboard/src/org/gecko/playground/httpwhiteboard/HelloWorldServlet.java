package org.gecko.playground.httpwhiteboard;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.http.whiteboard.annotations.RequireHttpWhiteboard;
@Component(service = Servlet.class, 
           scope=ServiceScope.PROTOTYPE,
           property= {"osgi.http.whiteboard.servlet.pattern=/hello", "osgi.http.whiteboard.context.name=hello"})
@RequireHttpWhiteboard
public class HelloWorldServlet extends javax.servlet.http.HttpServlet {
 
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        resp.getWriter().println("Hello World");
    }
}