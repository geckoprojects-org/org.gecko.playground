package org.gecko.playground.exchange.impl;

import java.util.concurrent.BlockingDeque;

import org.osgi.service.log.Logger;

public class Subscriber<T> implements Runnable {

	private BlockingDeque<? extends T> queue;
	private Handler<? super T> handler;
	private Logger log;

	public Subscriber(BlockingDeque<? extends T> queue, Handler<? super T> handler, Logger log) {
		this.queue = queue;
		this.handler = handler;
		this.log = log;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				T item = queue.takeLast();
				handler.handle(item);
			} catch (InterruptedException e) {
				// Exit from main loop
				break;
			} catch (Exception e) {
				if (log != null) {
					log.error("Error handling event: ", e);
				} else {
					System.err.println("Error handling event: ");
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
