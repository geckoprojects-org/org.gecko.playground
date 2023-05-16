package org.gecko.playground.exchange.api;

import org.gecko.playground.model.orders.Order;
import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public abstract class ExchangeListener {
	/**
	 * An order was submitted to an exchange
	 * 
	 * @param order
	 */
	public void orderSubmitted(String exchangeId, Order order) {};

	/**
	 * An order was cancelled.
	 * 
	 * @param order
	 *            The order as it existed just prior to cancellation.
	 */
	public void orderCancelled(String exchangeId, Order order) {};

	/**
	 * An order expired.
	 * 
	 * @param order
	 *            The order as it existed just prior to expiration.
	 */
	public void orderExpired(String exchangeId, Order order) {};
}
