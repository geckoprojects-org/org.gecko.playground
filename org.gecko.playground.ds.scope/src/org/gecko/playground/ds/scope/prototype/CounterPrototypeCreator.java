package org.gecko.playground.ds.scope.prototype;

import java.util.Map;

import org.gecko.playground.ds.scope.Counter;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

//@Component
public class CounterPrototypeCreator {
	
	private ServiceRegistration<Counter> registration;

	@Activate
	public void activate(BundleContext context) {
		Map<String, Object> props = Map.of("protoCounter", "true");
		registration = context.registerService(Counter.class, new CounterPrototypeFactory(), FrameworkUtil.asDictionary(props));
	}
	
	@Deactivate
	public void deactivate() {
		registration.unregister();
	}


}
