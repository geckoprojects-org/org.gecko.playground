package org.gecko.playground.exchange.shell;

import static java.util.Objects.isNull;

import org.gecko.playground.exchange.api.Exchange;
import org.gecko.playground.model.orders.Order;
import org.gecko.playground.model.orders.Side;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.apache.felix.service.command.annotations.GogoCommand;

@Component (service = Object.class  
//property = { "osgi.command.scope=exchange", 
//			 "osgi.command.function=orders",
//			 "osgi.command.function=askOrder"}
)
@GogoCommand(scope = "exchange", function = {"orders", "askOrder"})
public class ExchangeCommand {
	
	private Exchange exchange;
	
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
	
	public void askOrder(String symbol, int quantity, long price) {
		System.out.println("Ask for order of " + quantity + " for " + symbol + " at the price of " + price + " cent." );
		Order o = new Order(symbol, Side.Ask, quantity, price);
		try {
			exchange.submitOrder(o);
			System.out.println("Order was submitted ...");
		} catch (Exception e) {
			System.out.println("Error submitting the order: " + e.getMessage());
		}
	}

}
