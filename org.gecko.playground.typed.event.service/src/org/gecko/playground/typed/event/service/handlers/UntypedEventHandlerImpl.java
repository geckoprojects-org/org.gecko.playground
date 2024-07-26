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
import org.osgi.service.typedevent.UntypedEventHandler;
import org.osgi.service.typedevent.propertytypes.EventTopics;

/**
 * 
 * @author ilenia
 * @since Jul 26, 2024
 */
@Component(name="UntypedEventHandler", immediate=true, service=UntypedEventHandler.class)
@EventTopics("typed/evt/service/untyped")
public class UntypedEventHandlerImpl implements UntypedEventHandler {

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.typedevent.UntypedEventHandler#notifyUntyped(java.lang.String, java.util.Map)
	 */
	@Override
	public void notifyUntyped(String topic, Map<String, Object> event) {
		System.out.println("----------------------");
		System.out.println("Received UNTYPED event");
		event.forEach((k,v) -> {
			System.out.println(String.format("Property: %s  Value: %s", k, v.toString()));
		});
		System.out.println("----------------------");
	}

}
