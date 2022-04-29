/**
 * Copyright (c) 2012 - 2022 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.playground.promise;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author mark
 * @since 28.01.2022
 */
public class DBDriver {
	
	static interface ConnectionListener {
		void onConnected(String result);
		void onConnectionError(Throwable t, String message);
		void onDisconnected(String result);
	}

	/**
	 * Creates a new instance.
	 */
	public DBDriver() {
		withError = false;
	}

	/**
	 * Creates a new instance.
	 */
	public DBDriver(boolean error) {
		withError = error;
	}

	private final boolean withError;
	private String connectionId;
	private ConnectionListener listener;

	public void connectAsync() {
		Executors.newScheduledThreadPool(1).schedule(this::doConnect, 5, TimeUnit.SECONDS);
	}

	private void doConnect() {
		if (listener == null) {
			return;
		}
		System.out.println("Try connecting to database ... (" + Thread.currentThread().getName() + ")");
		if (withError) {
			Throwable t = new IllegalStateException("Cannot connect to database");
			listener.onConnectionError(t, t.getMessage());
		} else {
			connectionId = UUID.randomUUID().toString();
			listener.onConnected(connectionId);
		}		
	}

	public void disconnect() {
		if (listener != null && connectionId != null) {
			listener.onDisconnected(connectionId);
			connectionId = null;
		}
	}

	/**
	 * Returns the listener.
	 * @return the listener
	 */
	public ConnectionListener getListener() {
		return listener;
	}

	/**
	 * Sets the listener.
	 * @param listener the listener to set
	 */
	public void setListener(ConnectionListener listener) {
		this.listener = listener;
	}

}
