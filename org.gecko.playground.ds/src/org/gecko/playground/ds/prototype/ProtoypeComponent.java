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
package org.gecko.playground.ds.prototype;

import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * 
 * @author mark
 * @since 29.04.2022
 */
//@Component(scope=ServiceScope.PROTOTYPE)
@Component(scope=ServiceScope.SINGLETON)
public class ProtoypeComponent implements Counter{
	
	private final AtomicInteger counter = new AtomicInteger();
	
	public int getCurrentCount() {
		return counter.incrementAndGet();
	}

}
