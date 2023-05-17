package org.gecko.playground.ds.filter;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class GreeterComponent {
	
	private GreeterService greeter;
	
	@Activate
	public void activate() {
		System.out.println("Say greeting in german: " + greeter.greet("Emil"));
	}

	@Reference(target = "(lang=DE)")
	public void setGreeter(GreeterService greeter) {
		this.greeter = greeter;
	}
	
	public void unsetGreeter(GreeterService greeter) {
		this.greeter = null;
	}
	

}
