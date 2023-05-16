package org.gecko.playground.exchange.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.UUID;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.gecko.playground.exchange.impl.ExchangeImpl;
import org.gecko.playground.exchange.impl.OrderEventHandler;
import org.gecko.playground.model.orders.Fill;
import org.gecko.playground.model.orders.Order;
import org.gecko.playground.model.orders.Side;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.log.Logger;

@ExtendWith(MockitoExtension.class)
public class OrderEventHandlerTest {
	
	private final ExchangeImpl exchange = new ExchangeImpl();
	
	private BlockingDeque<Fill> fillQueue;
	private Logger log;

	@BeforeEach
	protected void setUp() throws Exception {
		fillQueue = new LinkedBlockingDeque<Fill>();
		log = mock(Logger.class);
	}
	
	@AfterEach
	protected void tearDown() throws Exception {
	}
	
	private Order createSubmitOrderEvent(String symbol, Side side, long qty, long price, long expiry) {
		return new Order(UUID.randomUUID(), 0, symbol, side, qty, price, expiry);
	}
	
	@Test
	public void testExactMatchOrderFill() throws Exception {
		OrderEventHandler handler = new OrderEventHandler(exchange, fillQueue);
		
		// Buy 1000
		handler.handle(createSubmitOrderEvent("USDCAD", Side.Bid, 1000, 50, 0));
		
		// Sell 1000
		handler.handle(createSubmitOrderEvent("USDCAD", Side.Ask, 1000, 50, 0));

		// Sell 50
		handler.handle(createSubmitOrderEvent("USDCAD", Side.Ask, 50, 50, 0));
		
		// Check fill events
		Fill fill = fillQueue.takeLast();
		assertEquals(1000, fill.getFillQuantity());

		Thread.sleep(500);
		assertEquals(0, fillQueue.size());
	}
	
	@Test
	public void testExactMatchOrderFillReverse() throws Exception {
		OrderEventHandler handler = new OrderEventHandler(exchange, fillQueue);
		
		// Sell 1000
		handler.handle(createSubmitOrderEvent("USDCAD", Side.Ask, 1000, 50, 0));
		
		// Buy 1000
		handler.handle(createSubmitOrderEvent("USDCAD", Side.Bid, 1000, 50, 0));

		// Buy 50
		handler.handle(createSubmitOrderEvent("USDCAD", Side.Bid, 50, 50, 0));
		
		// Check fill events
		Fill fill = fillQueue.takeLast();
		assertEquals(1000, fill.getFillQuantity());

		Thread.sleep(500);
		assertEquals(0, fillQueue.size());
	}
	
	@Test
	public void testPartialFill() throws Exception {
		OrderEventHandler handler = new OrderEventHandler(exchange, fillQueue);
		// Buy 100 at 100
		handler.handle(createSubmitOrderEvent("MSFT", Side.Bid, 100, 100, 0));
		// Buy 100 at 101
		handler.handle(createSubmitOrderEvent("MSFT", Side.Bid, 100, 101, 0));
		// Sell 100 at 200
		handler.handle(createSubmitOrderEvent("MSFT", Side.Ask, 100, 200, 0));
		// Sell 50 at 101 -- this should trigger a partial fill
		handler.handle(createSubmitOrderEvent("MSFT", Side.Ask, 50, 101, 0));
		
		// Check fill events
		Fill fill = fillQueue.removeFirst();
		assertEquals(50, fill.getFillQuantity());
		assertEquals(101, fill.getBidOrder().getPrice());
		assertEquals(101, fill.getAskOrder().getPrice());
		assertEquals(50, fill.getBidOrder().getQuantity());
		assertEquals(0, fill.getAskOrder().getQuantity());
	}
	
	@Test
	public void testOverflow() throws Exception {
		OrderEventHandler handler = new OrderEventHandler(exchange, fillQueue);
		// Buy 10 at 100
		handler.handle(createSubmitOrderEvent("MSFT", Side.Bid, 10, 100, 0));
		// Buy 10 at 100
		handler.handle(createSubmitOrderEvent("MSFT", Side.Bid, 10, 100, 0));
		
		// Sell 10 at 100
		handler.handle(createSubmitOrderEvent("MSFT", Side.Ask, 10, 100, 0));
		// Sell 10 at 100
		handler.handle(createSubmitOrderEvent("MSFT", Side.Ask, 10, 100, 0));
		
		assertEquals(2, fillQueue.size());
	}
	
	@Test
	public void testMultipleFills() throws Exception {
		OrderEventHandler handler = new OrderEventHandler(exchange, fillQueue);
		
		// Sell 1000, no fills
		handler.handle(createSubmitOrderEvent("USDCAD", Side.Ask, 1000, 50, 0));
		
		// Buy 100
		handler.handle(createSubmitOrderEvent("USDCAD", Side.Bid, 100, 55, 0));
		
		// Buy 2000
		handler.handle(createSubmitOrderEvent("USDCAD", Side.Bid, 2000, 55, 0));

		// Verify interactions
		Fill fill;
		
		fill = fillQueue.takeLast();
		assertEquals(100, fill.getFillQuantity());
		
		fill = fillQueue.takeLast();
		assertEquals(900, fill.getFillQuantity());
		
		Thread.sleep(500);
		assertEquals(0, fillQueue.size());
	}
	
	@Test
	public void testNoFillDueToSpread() throws Exception {
		OrderEventHandler handler = new OrderEventHandler(exchange, fillQueue);
		
		// Sell @50
		handler.handle(createSubmitOrderEvent("USDCAD", Side.Ask, 1000, 50, 0));
		
		// Buy @49
		handler.handle(createSubmitOrderEvent("USDCAD", Side.Ask, 100, 49, 0));
		
		Thread.sleep(1000); // that should do it
		assertEquals(0, fillQueue.size());
	}
	
	/*
	@Test
	public void testCancelOrder() throws Exception {
		FillListener mockListener = mock(FillListener.class);
		
		ExchangeImpl exchange = new ExchangeImpl();
		exchange.addFillListener(mockListener);
		
		// Sell
		Order sell = exchange.submitOrder(-1, "USDCAD", Side.Ask, 1000, 50, 0);
		assertEquals(1000, sell.getQuantity());
		
		// Cancel
		long cancelledQty = sell.cancel();
		assertEquals(1000, cancelledQty);
		
		// Buy - no fill
		Order buy = exchange.submitOrder(-1, "USDCAD", Side.Bid, 1000, 50, 0);
		assertEquals(1000, buy.getQuantity());
		
		verifyZeroInteractions(mockListener);
	}
	*/
	
}
