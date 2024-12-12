package org.gecko.playground.ds.filter;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component(name="CGS", configurationPolicy = ConfigurationPolicy.REQUIRE)
public class ConfigurableGreeterComponent {
	
	private GreeterService greeter;
	
	@Activate
	public void activate() {
		System.out.println("Activate configurable Greeter");
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("De-Activate configurable Greeter");
	}
	
	@Reference(name = "gs")
	public void setGreeter(GreeterService greeter) {
		System.out.println("Set Greeter Service " + greeter);
		this.greeter = greeter;
		doGreet();
	}
	
	public void unsetGreeter(GreeterService greeter) {
		System.out.println("Un-Set Greeter Service" + greeter);
		this.greeter = null;
	}
	
	private void doGreet() {
		System.out.println("Configuration says: " + greeter.greet("Emil"));
	}

}
