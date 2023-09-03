package org.gecko.playground.ds.filter;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

@Component(name="CGS", configurationPolicy = ConfigurationPolicy.REQUIRE)
public class ConfigurableGreeterComponent {
	
	private GreeterService greeter;
	
	@Activate
	public void activate() {
		System.out.println("Say greeting depending on the configuration: " + greeter.greet("Emil"));
	}

	@Reference(name = "gs")
	public void setGreeter(GreeterService greeter) {
		this.greeter = greeter;
	}
	
	public void unsetGreeter(GreeterService greeter) {
		this.greeter = null;
	}
	

}
