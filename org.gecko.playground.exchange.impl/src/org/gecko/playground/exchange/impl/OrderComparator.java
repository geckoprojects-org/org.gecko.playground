package org.gecko.playground.exchange.impl;

import java.util.Comparator;

import org.gecko.playground.model.orders.Order;
import org.gecko.playground.model.orders.Side;

public class OrderComparator implements Comparator<Order> {

	@Override
	public int compare(Order o1, Order o2) {
		// Bids before Asks
		if (o1.getSide() == Side.Ask && o2.getSide() == Side.Bid)
			return 1;
		if (o1.getSide() == Side.Bid && o2.getSide() == Side.Ask)
			return -1;
		
		// Lowest priced first
		long priceDiff = o1.getPrice() - o2.getPrice();
		if (priceDiff < 0L)
			return -1;
		else if (priceDiff > 0L)
			return 1;
		
		// Finally, order by ID
		return o1.getId().compareTo(o2.getId());
	}

}
