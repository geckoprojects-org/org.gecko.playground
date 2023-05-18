package org.gecko.playground.eventadmin;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

@Component(immediate=true, name="org.gecko.playground.exchange.prices.feed")
public class RandomPriceFeed implements Runnable {
	
	private static final String TOPIC_PREFIX = "PRICES/STOCKS/NASDAQ/";
	private static final long MINIMUM_SLEEP_TIME = 100;

	
	private double startingPrice;
	private String symbol;
	private long sleepTime;
	
	private Thread thread;
	private double delta;

	@Reference
	private EventAdmin eventAdmin;
	
	@Activate
	protected void activate() {
		symbol = "MSFT";
		startingPrice = 300;
		
		sleepTime = Math.max(MINIMUM_SLEEP_TIME, 500);
		delta = 10;
		thread = new Thread(this);
		thread.start();
	}
	
	@Deactivate
	protected void deactivate() {
		thread.interrupt();
	}
	
	public void run() {
		double price = startingPrice;
		Random random = new Random();

		try {
			while (!Thread.currentThread().isInterrupted()) {
				postUpdate(symbol, price, System.currentTimeMillis());
				
				// Sleep
				Thread.sleep(sleepTime);

				// Randomly adjust price by up to +/- delta
				double adjustment = random.nextDouble() * delta * 2 - delta;
				price = Math.max(0, price + adjustment);
			}
		} catch (InterruptedException e) {
			// Exit quietly
		}
	}
	
	private void postUpdate(String symbol, double price, long time) {
		Map<String, Object> props = new HashMap<>();
		props.put("symbol" , symbol);
		props.put("time" , time);
		props.put("price" , price);
//		eventAdmin.sendEvent(new Event(TOPIC_PREFIX + symbol, props));
		eventAdmin.postEvent(new Event(TOPIC_PREFIX + symbol, props));
	}
}
