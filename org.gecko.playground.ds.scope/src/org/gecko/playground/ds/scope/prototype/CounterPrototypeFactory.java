package org.gecko.playground.ds.scope.prototype;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.gecko.playground.ds.scope.Counter;
import org.osgi.framework.Bundle;
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceRegistration;

public class CounterPrototypeFactory implements PrototypeServiceFactory<Counter> {
	
	private final Map<ServiceRegistration<Counter>, Counter> counterMap = new ConcurrentHashMap<>();

	@Override
	public Counter getService(Bundle bundle, ServiceRegistration<Counter> registration) {
		Counter counter = new CounterImpl();
		counterMap.put(registration, counter);
		return counter;
	}

	@Override
	public void ungetService(Bundle bundle, ServiceRegistration<Counter> registration, Counter service) {
		Counter old = counterMap.remove(registration);
		if (old == null) {
			System.out.println("Counter was already removed");
		}
	}

}
