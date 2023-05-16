package org.gecko.playground.exchange.impl;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.gecko.playground.model.orders.Fill;
import org.gecko.playground.model.orders.Order;
import org.gecko.playground.model.orders.Side;

class OrderEventHandler implements Handler<Order> {
	
	private final ConcurrentMap<String, TreeSet<Order>> bids = new ConcurrentHashMap<String, TreeSet<Order>>();
	private final ConcurrentMap<String, TreeSet<Order>> asks = new ConcurrentHashMap<String, TreeSet<Order>>();
	
	private final ExchangeImpl exchange;
	private final BlockingDeque<Fill> fillQueue;

	OrderEventHandler(ExchangeImpl exchange, BlockingDeque<Fill> fillQueue) {
		this.exchange = exchange;
		this.fillQueue = fillQueue;
	}

	@Override
	public void handle(Order order) throws Exception {
		long qtyRemaining = order.getQuantity();

		// Look for immediately available matches
		TreeSet<Order> oppositeBook = getBook(order.getSymbol(), order.getSide().opposite());
		Iterator<Order> iter = order.getSide().opposite() == Side.Ask ? oppositeBook.iterator() : oppositeBook.descendingIterator();
		while (iter.hasNext()) {
			Order matchingOrder = iter.next();
			if (qtyRemaining == 0)
				break;
			
			boolean canFill = (order.getSide() == Side.Bid && matchingOrder.getPrice() <= order.getPrice()) || order.getSide() == Side.Ask && matchingOrder.getPrice() >= order.getPrice();
			if (!canFill)
				break;
			
			long fillQty = Math.min(qtyRemaining, matchingOrder.getQuantity());
			
			Order bid, ask;
			if (order.getSide() == Side.Bid) {
				bid = order;
				ask = matchingOrder;
			} else {
				bid = matchingOrder;
				ask = order;
			}
			
			// Send a fill event onto the fills queue
			Fill fill = new Fill(fillQty, bid, ask, System.currentTimeMillis());
			fillQueue.putFirst(fill);
			
			qtyRemaining -= fillQty;
			
			matchingOrder.setQuantity(matchingOrder.getQuantity() - fillQty);
			if (matchingOrder.getQuantity() == 0)
				iter.remove();
		}
		order.setQuantity(qtyRemaining);
		
		// If any quantity remaining, record order in the order book
		if (qtyRemaining > 0) {
			SortedSet<Order> book = getBook(order.getSymbol(), order.getSide());
			book.add(order);
		}
		
		exchange.orderSubmitted(order);
	}

	private TreeSet<Order> getBook(String symbol, Side side) {
		ConcurrentMap<String, TreeSet<Order>> map = (side == Side.Bid) ? bids : asks;
		TreeSet<Order> book = map.get(symbol);
		if (book == null) {
			book = new TreeSet<Order>(new OrderComparator());
			TreeSet<Order> previous = map.putIfAbsent(symbol, book);
			if (previous != null)
				book = previous;
		}
		return book;
	}
	
}
