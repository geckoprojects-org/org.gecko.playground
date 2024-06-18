/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.playground.ds.optional.impl;

import org.gecko.playground.log.Log;
import org.osgi.service.component.annotations.Component;

/**
 * 
 * @author mark
 * @since 12.06.2024
 */
@Component
public class SimpleLog implements Log {

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.log.Log#logMessage(java.lang.String)
	 */
	@Override
	public void logMessage(String message) {
		System.out.println("[SimpleLog]" + message);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.log.Log#info(java.lang.String)
	 */
	@Override
	public String info(String message) {
		System.out.println("[SimpleLog][INFO]" + message);
		return "[INFO]" + message;
	}

}
