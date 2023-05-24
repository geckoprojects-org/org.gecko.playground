package org.gecko.playground.ds.dynamics;

import static java.util.Objects.isNull;

import java.util.concurrent.atomic.AtomicReference;

import org.gecko.playground.exchange.api.Exchange;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

@Component
public class ExchangeComponent {
	
	private AtomicReference<Exchange> exchangeRef = new AtomicReference<Exchange>();
	
	@Activate
	public void activate() {
		System.out.println("Activate dynamic exchange component");
		Exchange exchange = exchangeRef.get();
		if (isNull(exchange)) {
			System.out.println("Exchange not available!");
		} else {
			System.out.println(String.format("There are %d orders", exchange.getAllOrders().size()));
		}
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("De-Activate dynamic exchange component");
	}
	
	@Reference(cardinality = ReferenceCardinality.OPTIONAL, policy = ReferencePolicy.DYNAMIC)
//	@Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.DYNAMIC)
	public void setExchange(Exchange exchange) {
		System.out.println("Dynamic set exchange " + exchange.toString());
		exchangeRef.set(exchange);
	}
	
	public void unsetExchange(Exchange exchange) {
		System.out.println("Dynamic unset exchange " + exchange.toString());
		exchangeRef.compareAndSet(exchange, null);
	}

}
