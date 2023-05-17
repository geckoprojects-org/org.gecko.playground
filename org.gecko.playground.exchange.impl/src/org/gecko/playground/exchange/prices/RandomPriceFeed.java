package org.gecko.playground.exchange.prices;

import java.util.Random;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

@Component(immediate=true, name="org.gecko.playground.exchange.prices.feed")
public class RandomPriceFeed implements Runnable {
	
	private static final String TOPIC_PREFIX = "PRICES/STOCKS/NASDAQ/";
	private static final long MINIMUM_SLEEP_TIME = 100;

	
	private double startingPrice;
	private String symbol;
	private long sleepTime;
	
	private Thread thread;
	private double delta;

	// TODO (Lab 17): bind a reference to the EventAdmin service
//	@Reference
//	private EventAdmin eventAdmin;
	
	@Activate
	protected void activate() {
		// TODO (Lab 18): get the following two settings from config
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
		// TODO (Lab 17): Create and send the event containing symbol, current time and price
		// Tip: create the topic using the TOPIC_PREFIX constant, e.g. TOPIC_PREFIX + symbol
//		Dictionary<String, Object> props = FrameworkUtil.asDictionary(Map.of(null, null));
//		Event e = new Event(TOPIC_PREFIX + symbol, props);
//		eventAdmin.postEvent(e);
	}
}
