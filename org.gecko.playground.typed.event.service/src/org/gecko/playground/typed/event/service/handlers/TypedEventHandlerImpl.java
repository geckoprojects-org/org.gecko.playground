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

import org.gecko.playground.typed.event.service.EventData;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.typedevent.TypedEventHandler;
import org.osgi.service.typedevent.propertytypes.EventTopics;

/**
 * 
 * @author ilenia
 * @since Jul 26, 2024
 */
@Component(name="TypedEventHandler", immediate=true, service=TypedEventHandler.class)
@EventTopics("typed/evt/service/typed")
public class TypedEventHandlerImpl implements TypedEventHandler<EventData> {

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.typedevent.TypedEventHandler#notify(java.lang.String, java.lang.Object)
	 */
	@Override
	public void notify(String topic, EventData event) {
		System.out.println("----------------------");
		System.out.println("Received TYPED event");
		System.out.println(String.format("Message is: %s", event.getMessage()));
		System.out.println("----------------------");
		
	}

}
