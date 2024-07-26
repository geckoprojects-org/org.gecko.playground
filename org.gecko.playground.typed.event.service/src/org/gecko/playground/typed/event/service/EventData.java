/**
 * Copyright (c) 2012 - 2018 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.playground.typed.event.service;

public class EventData {

	private String message;
	
	public EventData(String message) {
		setMessage(message);
	}
	
	private void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

}
