package org.gecko.playground.ds.whiteboard.logger;

import org.gecko.playground.exchange.api.ExchangeListener;
import org.gecko.playground.log.Log;
import org.gecko.playground.model.orders.Order;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = ExchangeListener.class,immediate = true)
public class LoggingExchangeListener extends ExchangeListener {
	
	private Log log;
	
	public Log getLog() {
		return log;
	}
	
	@Reference
	public void setLog(Log log) {
		this.log = log;
	}
	
	@Override
	public void orderSubmitted(String exchangeId, Order order) {
		log.logMessage("Log Exchange Listener: Order submitted" + order.toString());
	}

	@Override
	public void orderCancelled(String exchangeId, Order order) {
		log.logMessage("Log Exchange Listener: order cancelled: " + order.toString());
	}

	@Override
	public void orderExpired(String exchangeId, Order order) {
		log.logMessage("Log Exchange Listener: order expired: " + order.toString());
	}
	
}
