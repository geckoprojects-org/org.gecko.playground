package org.gecko.playground.ds.simple.shell;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;

//@Component (service = Object.class , 
//	property = { "osgi.command.scope=hello", 
//				 "osgi.command.function=sagHallo",
//				 "osgi.command.function=sayHello"})
public class HelloCommand {
	
	@Activate
	public void activate()
	{
		System.out.println("Activate Command");
	}
	@Deactivate
	public void deactivate()
	{
		System.out.println("De-Activate Command");
	}
	public void sayHello (String name, int age) {
		System.out.println("I wanted to say Hello!" + name + " " + age);
	}
	
	public void sagHallo () {
		System.out.println("Ich wollte nur Hallo sagen!");
	}
	
}