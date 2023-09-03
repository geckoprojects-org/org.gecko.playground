package org.gecko.playground.ds.simple.shell;

import org.osgi.service.component.annotations.Component;

@Component (service = Object.class , 
	property = { "osgi.command.scope=hello", 
				 "osgi.command.function=sagHallo",
				 "osgi.command.function=sayHello"})
public class HelloCommand {
	
	public void sayHello () {
		System.out.println("I wanted to say Hello!");
	}
	
	public void sagHallo () {
		System.out.println("Ich wollte nur Hallo sagen!");
	}
	
}