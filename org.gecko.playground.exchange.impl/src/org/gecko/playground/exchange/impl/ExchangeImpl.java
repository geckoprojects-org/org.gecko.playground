package org.gecko.playground.exchange.impl;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;

import org.gecko.playground.exchange.api.Exchange;
import org.gecko.playground.model.orders.Fill;
import org.gecko.playground.model.orders.Order;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

@Component(
		property = {
				"name=Local",
				"symbols=MSFT",
				"symbols=GOOG",
				"symbols=AAPL"
		})
public class ExchangeImpl implements Exchange {
	
	private static final AtomicLong instanceCounter = new AtomicLong(0L);
	
	private final ConcurrentMap<UUID, Order> orders = new ConcurrentHashMap<UUID, Order>();
	private final BlockingDeque<Order> orderQueue = new LinkedBlockingDeque<Order>(128);
	private final BlockingDeque<Fill> fillQueue = new LinkedBlockingDeque<Fill>(128);
	private final ExecutorService executor = Executors.newCachedThreadPool();
	
	private String id;

	@Reference(service = LoggerFactory.class, cardinality = ReferenceCardinality.OPTIONAL, policyOption = ReferencePolicyOption.GREEDY)
	private Logger log;

	// TODO: bind to ExchangeListener services.
	// You will need to add a final field of type List<ExchangeListener>.
	
	/**
	 * Activate without OSGi ComponentContext. Used for tests.
	 * @param configProps
	 */
	void activate(Map<String, Object> configProps) {
		activate(configProps, null);
	}
	
	@Activate
	void activate(Map<String, Object> configProps, ComponentContext context) {
		Handler<Fill> fillHandler = new Handler<Fill>() {
			public void handle(Fill event) {
				fireFill(event);
			}
		};
		executor.submit(new Subscriber<Fill>(fillQueue, fillHandler, log));
		executor.submit(new Subscriber<Order>(orderQueue, new OrderEventHandler(this, fillQueue), log));
		
		// Get my ID as <framwork uuid>/<service.id> 
		if (context != null) {
			String frameworkUUID = context.getBundleContext().getProperty(Constants.FRAMEWORK_UUID);
			ServiceReference<?> ref = context.getServiceReference();
			long serviceId = ref != null ? (Long) ref.getProperty(Constants.SERVICE_ID) : 0;
			id = String.format("%s/%d", frameworkUUID, serviceId);
		} else {
			id = String.format("%s/%d", UUID.randomUUID().toString(), instanceCounter.getAndIncrement()); 
		}
	}
	
	@Deactivate
	void deactivate() {
		executor.shutdown();
	}
	
	@Override
	public void submitOrder(Order order) throws Exception {
		orderQueue.putFirst(order);
	}
	
	@Override
	public Order findOrder(UUID id) {
		return orders.get(id);
	}
	
	@Override
	public Collection<Order> getAllOrders() {
		return orders.values();
	}
	
	void orderSubmitted(Order order) {
		orders.put(order.getId(), order);
		fireOrderSubmitted(order);
	}
	
	private void fireOrderSubmitted(Order order) {
		// TODO: iterate over ExchangeListeners and call orderSubmitted(exchangeId, order).
	}
	
	private void fireFill(Fill fill) {
		// this method can be ignored
	}

}
