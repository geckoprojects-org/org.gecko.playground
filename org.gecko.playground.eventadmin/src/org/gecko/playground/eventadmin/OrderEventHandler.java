package org.gecko.playground.eventadmin;

import org.gecko.playground.exchange.api.Exchange;
import org.gecko.playground.model.orders.Order;
import org.gecko.playground.model.orders.Side;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

@Component(property = "event.topics=PRICES/STOCKS/NASDAQ/MSFT")
public class OrderEventHandler implements EventHandler {
	
	@Reference
	private Exchange exchange;

	@Override
	public void handleEvent(Event event) {
		String s = (String) event.getProperty("symbol");
		long time = (long) event.getProperty("time");
		double price = (double) event.getProperty("price");
		Order o = new Order(s, Side.Bid, time, (long)price * 100);
		o.setQuantity(20);
		try {
			System.out.println("Place event order: " + o.toString());
			exchange.submitOrder(o);
		} catch (Exception e) {
			System.out.println("Error placing event order: " + e.getMessage());
		}
	}

}
