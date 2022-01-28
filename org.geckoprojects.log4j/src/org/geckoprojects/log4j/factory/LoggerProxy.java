package org.geckoprojects.log4j.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceRegistration;

public class LoggerProxy implements InvocationHandler {

	private final AtomicReference<LogManagerContext> contextRef = new AtomicReference<LogManagerContext>();
	private final ServiceRegistration<Logger> registration;
	private final Bundle bundle;
	private final Semaphore sem = new Semaphore(1);
	private final Semaphore noLogSem = new Semaphore(1);
	private Logger loggerProxy = null;
	private boolean disposed = false;

	public LoggerProxy(ServiceRegistration<Logger> registration, Bundle bundle) {
		this.registration = Objects.requireNonNull(registration, "No service registration was provided");
		this.bundle = Objects.requireNonNull(bundle, "No bundle was provided");
	}

	public LoggerProxy(ServiceRegistration<Logger> registration, Bundle bundle, LogManagerContext context) {
		this.registration = Objects.requireNonNull(registration, "No service registration was provided");
		this.bundle = Objects.requireNonNull(bundle, "No bundle was provided");
		contextRef.compareAndSet(null, context);
	}

	public Bundle getBundle() {
		return bundle;
	}

	public ServiceRegistration<Logger> getRegistration() {
		return registration;
	}

	public void setContext(LogManagerContext context) {
		runWithSemaphore(()->{
			LogManagerContext current = contextRef.get();
			if (contextRef.compareAndSet(current, context) && context != null) {
				noLogSem.release();
			}
			return null;
		});
	}

	/**
	 * We set the current context to <code>null</code>. We also set a lock, so that the invoke
	 * method can know, that we currently have no {@link LogManagerContext}.
	 * This can temporarily happen, when a log4j-core bundle is replaced or updated, maybe because 
	 * of configuration changes in connected fragments.
	 * @param context
	 * @throws InterruptedException
	 */
	public void unsetContext(LogManagerContext context) {
		runWithNoLogSemaphore(()->{
			contextRef.compareAndSet(context, null);
			if (context != null) {
				context.shutdownContext(bundle);
			}
			return null;
		}, null, null);
	}

	public Logger getLoggerProxy() {
		if (loggerProxy == null) {
			loggerProxy = (Logger) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {Logger.class}, this);
		}
		return loggerProxy;
	}
	
	private <T> T runWithNoLogSemaphore(Callable<T> callable, Callable<T> elseCallable, Runnable finallyRun) {
		return runWithSemaphore(()->{
			// If we get no lock, the context is already unset and not yet replaced
			try {
				if(noLogSem.tryAcquire(200, TimeUnit.MILLISECONDS)) {
					try {
						if (disposed) {
							throw new IllegalStateException("Logger proxy is already disposed");
						}
						return callable.call();
					} finally {
						if (finallyRun != null) {
							finallyRun.run();
						}
					}
				} else {
					if (elseCallable != null) {
						T value = elseCallable.call();
						if (value != null) {
							return value;
						}
					}
					return null;
				}
			} catch (IllegalStateException e) {
				throw e;
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		});
	}
	
	private <T> T runWithSemaphore(Callable<T> callable) {
		try {
			if (sem.tryAcquire(200, TimeUnit.MILLISECONDS)) {
				try {
					if (disposed) {
						throw new IllegalStateException("Logger proxy is already disposed");
					}
					return callable.call();
				} finally {
					sem.release();
				}
			} else {
				throw new IllegalStateException("Cannot get lock within 200ms to unset the current Log4J LogManager");
			}
		} catch (IllegalStateException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return runWithNoLogSemaphore(()->{
			LogManagerContext lmContext = contextRef.get();
			Logger delegate = lmContext.getLogger(bundle);
			return method.invoke(delegate, args);
		},()->{
			throw new IllegalStateException("Cannot get Log4J implementation within 200ms. It seems that Log4J has gone");
		} , noLogSem::release);
	}
	
	public void dispose() {
		try {
			if (sem.tryAcquire(200l, TimeUnit.MILLISECONDS)) {
				try {
					LogManagerContext context = contextRef.get();
					if (context != null) {
						context.shutdownContext(bundle);
					}
				} finally {
					disposed = true;
					sem.release();
				}
			} else {
				disposed = true;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// releasing all semaphores, if there are any
			sem.release();
			noLogSem.release();
			
		}
	}

}
