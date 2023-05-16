package org.gecko.playground.exchange.api;

import java.util.Collection;
import java.util.UUID;

import org.gecko.playground.model.orders.Order;
import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface Exchange {

	void submitOrder(Order order) throws Exception;

	Order findOrder(UUID id);
	
	Collection<Order> getAllOrders();

}
