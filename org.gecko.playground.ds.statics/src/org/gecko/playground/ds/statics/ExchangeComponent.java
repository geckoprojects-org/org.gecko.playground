package org.gecko.playground.ds.statics;

import static java.util.Objects.isNull;

import org.gecko.playground.exchange.api.Exchange;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

@Component
public class ExchangeComponent {
	
	private Exchange exchange;
	
	@Activate
	public void activate() {
		System.out.println("Activate exchange component");
		if (isNull(exchange)) {
			System.out.println("Exchange not available!");
		} else {
			System.out.println(String.format("There are %d orders", exchange.getAllOrders().size()));
		}
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("De-Activate exchange component");
	}
	
//	@Reference
	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	public void setExchange(Exchange exchange) {
		System.out.println("Set exchange " + exchange.toString());
		this.exchange = exchange;
	}
	
	public void unsetExchange(Exchange exchange) {
		System.out.println("Unset exchange " + exchange.toString());
		this.exchange = null;
	}

}
