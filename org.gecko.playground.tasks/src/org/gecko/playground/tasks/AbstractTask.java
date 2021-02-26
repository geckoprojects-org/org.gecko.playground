package org.gecko.playground.tasks;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.gecko.playground.tasks.spi.ProxyTaskInfo;
import org.osgi.util.promise.Promise;
import org.osgi.util.promise.PromiseFactory;

/**
 * An abstract implementation of an {@link Task}.
 *  
 * This class should be used instead of directly implementing the interface!
 * 
 * With that implementation the users don't have to deal with handling of the {@link Promise}s. The state handling is also already implemented.
 * 
 */
public abstract class AbstractTask implements Task {
	
	private final PromiseFactory pf;
	private volatile TaskState state = TaskState.INACTIVE;
	
	public AbstractTask() {
		pf = new PromiseFactory(Executors.newSingleThreadExecutor(new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r, getName());
				return t;
			}
		}));
	}

	@Override
	public final Promise<TaskInfo> activateTask() {
		return pf.submit(()->{
			state = TaskState.ACTIVATING;
			doActivate();
			return null;
		}).onFailure((v)->state = TaskState.ERROR)
				.onResolve(()->state = TaskState.ACTIVE)
				.map((i)->ProxyTaskInfo.create(AbstractTask.this));
	}

	@Override
	public final Promise<TaskInfo> deactivateTask() {
		return pf.submit(()->{
			state = TaskState.DEACTIVATING;
			doDeactivate();
			return null;
		}).onFailure((v)->state = TaskState.ERROR)
				.onResolve(()->state = TaskState.INACTIVE)
				.map((i)->ProxyTaskInfo.create(AbstractTask.this));
	}

	@Override
	public final TaskState getState() {
		return state;
	}
	
	/**
	 * To be implemented by user.
	 */
	abstract protected void doActivate();
	
	abstract protected void doDeactivate();

}
