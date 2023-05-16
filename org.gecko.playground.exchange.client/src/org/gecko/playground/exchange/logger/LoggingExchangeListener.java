package org.gecko.playground.exchange.logger;

import org.gecko.playground.ds.simple.logging.Log;
import org.gecko.playground.exchange.api.ExchangeListener;
import org.gecko.playground.model.orders.Order;

public class LoggingExchangeListener extends ExchangeListener {
	
	private Log log;
	
	public Log getLog() {
		return log;
	}
	
	public void setLog(Log log) {
		this.log = log;
	}

	@Override
	public void orderSubmitted(String exchangeId, Order order) {
		log.logMessage("LoggingExchangeListener: order submitted.");
	}

	@Override
	public void orderCancelled(String exchangeId, Order order) {
		log.logMessage("LoggingExchangeListener: order cancelled.");
	}

	@Override
	public void orderExpired(String exchangeId, Order order) {
		log.logMessage("LoggingExchangeListener: order expired.");
	}
	
}
