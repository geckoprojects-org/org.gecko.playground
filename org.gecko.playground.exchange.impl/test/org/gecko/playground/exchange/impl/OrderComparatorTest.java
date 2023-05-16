package org.gecko.playground.exchange.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.UUID;

import org.gecko.playground.exchange.impl.OrderComparator;
import org.gecko.playground.model.orders.Order;
import org.gecko.playground.model.orders.Side;
import org.junit.jupiter.api.Test;

public class OrderComparatorTest {

	@Test
	public void testBidOrdering() {
		Order o1 = new Order(UUID.randomUUID(), 0, "", Side.Bid, 100, 101, 0);
		Order o2 = new Order(UUID.randomUUID(), 0, "", Side.Bid, 100, 102, 0);
		Order o3 = new Order(UUID.randomUUID(), 0, "", Side.Bid, 100, 103, 0);
		
		OrderComparator c = new OrderComparator();
		assertTrue(c.compare(o1, o2) < 0);
		assertTrue(c.compare(o2, o3) < 0);
		
		TreeSet<Order> set = new TreeSet<Order>(c);
		set.add(o1);
		set.add(o3);
		set.add(o2);
		
		Iterator<Order> iter = set.descendingIterator();
		assertEquals(103, iter.next().getPrice());
		assertEquals(102, iter.next().getPrice());
		assertEquals(101, iter.next().getPrice());
	}

	@Test
	public void testAskOrdering() {
		Order o1 = new Order(UUID.randomUUID(), 0, "", Side.Ask, 100, 101, 0);
		Order o2 = new Order(UUID.randomUUID(), 0, "", Side.Ask, 100, 102, 0);
		Order o3 = new Order(UUID.randomUUID(), 0, "", Side.Ask, 100, 103, 0);
		
		OrderComparator c = new OrderComparator();
		TreeSet<Order> set = new TreeSet<Order>(c);
		set.add(o1);
		set.add(o3);
		set.add(o2);
		
		Iterator<Order> iter = set.iterator();
		assertEquals(101, iter.next().getPrice());
		assertEquals(102, iter.next().getPrice());
		assertEquals(103, iter.next().getPrice());
	}
}
