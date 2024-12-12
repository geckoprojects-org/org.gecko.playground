package org.gecko.playground.ds.filter;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

@Component(name="CGSMin", configurationPolicy = ConfigurationPolicy.REQUIRE)
public class ConfigurableMinCardinComponent {
	
	private List<GreeterService> greeters = new ArrayList<>();
	
	@Activate
	public void activate() {
		System.out.println("[min-card] Activate configurable Greeter");
		greeters.forEach(this::doGreet);
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("[min-card] De-Activate configurable Greeter");
	}
	
	@Reference(name = "gsmin", cardinality = ReferenceCardinality.MULTIPLE)
	public void addGreeter(GreeterService greeter) {
		System.out.println("[min-card] Add Greeter Service " + greeter);
		this.greeters.add(greeter);
	}
	
	public void removeGreeter(GreeterService greeter) {
		System.out.println("[min-card] Remove Greeter Service" + greeter);
		this.greeters.remove(greeter);
	}
	
	private void doGreet(GreeterService greeter) {
		System.out.println("[min-card] Configuration says: " + greeter.greet("Emil"));
	}

}
