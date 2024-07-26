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
package org.gecko.playground.typed.event.service;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.typedevent.TypedEventBus;

/**
 * 
 * @author ilenia
 * @since Jul 26, 2024
 */
@Component(name = "EventSender", service = EventSender.class, property = {
		"osgi.command.scope=tes", //
		"osgi.command.function=sendUntypedEvent", 
		"osgi.command.function=sendTypedEvent"
})
public class EventSender {
	
	@Reference
	TypedEventBus typedEventBus;
	
	public void sendUntypedEvent(String topic, String message) {
		Map<String, String> evtMap = new HashMap<>();
		evtMap.put("message", message);
		typedEventBus.deliverUntyped(topic, evtMap);
	}
	
	public void sendTypedEvent(String topic, String message) {
		EventData evtData = new EventData(message);
		typedEventBus.deliver(topic, evtData);
	}

}
