package org.gecko.playground.promise;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.Executors;

import org.gecko.playground.promise.DBDriver.ConnectionListener;
import org.osgi.annotation.bundle.Capability;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.condition.Condition;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;
import org.osgi.util.promise.Deferred;
import org.osgi.util.promise.Promise;
import org.osgi.util.promise.PromiseFactory;

/**
 * Component that initialized a database driver. If it connects successfully, a OSGi Condition will be started.
 * This shows how to use {@link Deferred} and {@link Promise} for such a task.
 * @author mark
 * @since 28.01.2022
 */
@Capability(namespace = "osgi.service", name = "Database Condition", attribute = {"objectClass=org.osgi.service.condition.Condition"})
@Component
public class DatabaseDriverComponent {
	
	@Reference(service = LoggerFactory.class)
	Logger logger;
	private final PromiseFactory pf = new PromiseFactory(Executors.newCachedThreadPool(), Executors.newScheduledThreadPool(2));
	private BundleContext bctx;
	private DBDriver driver;
	private ServiceRegistration<Condition> databaseReg;
	
	@Activate
	public void activate(BundleContext bctx) {
		this.bctx = bctx;
		final Deferred<String> dbDef = pf.deferred();
		/*
		 * After 15 seconds the database is connected and sends the resolved
		 * String to the defered. this happens in a different process/thread
		 */
		System.out.println("Scheduling database connection (" + Thread.currentThread().getName() + ")");
		driver = new DBDriver();
		driver.setListener(new ConnectionListener() {
			
			@Override
			public void onDisconnected(String result) {
				doDisconnected(result);
			}
			
			@Override
			public void onConnected(String result) {
				dbDef.resolve(result);
			}
			
			@Override
			public void onConnectionError(Throwable t, String message) {
				dbDef.fail(t);
			}
		});
		driver.connectAsync();
		Promise<String> p = dbDef.getPromise();
		/*
		 * We delay our processing for 2 seconds, for whatever reasons
		 */
		p.delay(2000).map(r->"CONID-" + r).onFailure(this::doConnectionFailed).onSuccess(this::doConnected);
		System.out.println("Finished activation (" + Thread.currentThread().getName() + ")");
	}
	
	@Deactivate
	public void deactivate() {
		if (driver != null) {
			driver.disconnect();
		}
	}

	private void doConnected(String result) {
		Dictionary<String, Object> prop = new Hashtable<>();
		prop.put("database.result", result);
		prop.put("database", true);
		databaseReg = bctx.registerService(Condition.class, Condition.INSTANCE, prop);
		System.out.println("Database ID: " + result);
	}

	/**
	 * @param result
	 */
	private void doDisconnected(String result) {
		System.out.println("Disconnected Database with ID: " + result);
		if (databaseReg != null) {
			databaseReg.unregister();
		}
	}

	private void doConnectionFailed(Throwable t) {
		System.out.println("Failed to establish a database connection: " + t.getMessage());
	}

}
