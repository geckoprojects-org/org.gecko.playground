package org.gecko.playground.exchange.shell;

import static java.util.Objects.isNull;

import org.gecko.playground.exchange.api.Exchange;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

@Component (service = Object.class , 
property = { "osgi.command.scope=exchange", 
			 "osgi.command.function=orders"})
public class ExchangeCommand {
	
	private Exchange exchange;
	
//	@Reference
	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	public void setExchange(Exchange exchange) {
		this.exchange = exchange;
	}
	
	public void unsetExchange(Exchange exchange) {
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
