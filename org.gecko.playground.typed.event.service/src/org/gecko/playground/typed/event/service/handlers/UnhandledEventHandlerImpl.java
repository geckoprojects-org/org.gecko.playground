/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.playground.typed.event.service.handlers;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.typedevent.UnhandledEventHandler;

/**
 * 
 * @author ilenia
 * @since Jul 26, 2024
 */
@Component(name="UnhandledEventHandler", immediate=true, service=UnhandledEventHandler.class)
public class UnhandledEventHandlerImpl implements UnhandledEventHandler {

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.typedevent.UnhandledEventHandler#notifyUnhandled(java.lang.String, java.util.Map)
	 */
	@Override
	public void notifyUnhandled(String topic, Map<String, Object> event) {
		System.out.println("----------------------");
		System.out.println(String.format("Received UNHANDLED event for topic %s", topic));
		event.forEach((k,v) -> {
			System.out.println(String.format("Property: %s  Value: %s", k, v.toString()));
		});
		System.out.println("----------------------");

	}

}
