package org.gecko.playground.ds.whiteboard.sysout;

import org.gecko.playground.exchange.api.ExchangeListener;
import org.gecko.playground.model.orders.Order;
import org.osgi.service.component.annotations.Component;

@Component(service = ExchangeListener.class,immediate = true)
public class SysoutExchangeListener extends ExchangeListener {
	
	@Override
	public void orderSubmitted(String exchangeId, Order order) {
		System.out.println("Sysout: Order submitted" + order.toString());
	}

	@Override
	public void orderCancelled(String exchangeId, Order order) {
		System.out.println("Sysout: order cancelled: " + order.toString());
	}

	@Override
	public void orderExpired(String exchangeId, Order order) {
		System.out.println("Sysout: order expired: " + order.toString());
	}
	
}
