package org.gecko.playground.ds.statics;

import static java.util.Objects.isNull;

import org.gecko.playground.exchange.api.Exchange;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

@Component (service = Object.class , 
property = { "osgi.command.scope=exchange", 
			 "osgi.command.function=orders"})
public class ExchangeCommand {
	
	private Exchange exchange;
	
	@Activate
	private void activate() {
		System.out.println("Activate order command ");
	}
	
	@Deactivate
	private void deactivate() {
		System.out.println("De-Activate order command ");
	}
	
//	@Reference
	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	public void setExchange(Exchange exchange) {
		System.out.println("Order Command: Set exchange " + exchange.toString());
		this.exchange = exchange;
	}
	
	public void unsetExchange(Exchange exchange) {
		System.out.println("Order Command: Unset exchange " + exchange.toString());
		this.exchange = null;
	}
	
	public void orders() {
		if (isNull(exchange)) {
			System.out.println("Exchange not available!");
		} else {
			System.out.println(String.format("There are %d orders", exchange.getAllOrders().size()));
		}
	}

}
