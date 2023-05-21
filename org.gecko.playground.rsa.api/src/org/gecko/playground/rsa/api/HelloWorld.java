/**
 * Copyright (c) 2012 - 2021 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.playground.rsa.api;

import org.osgi.annotation.versioning.ProviderType;

/**
 * 
 * @author mark
 * @since 08.11.2021
 */
@ProviderType
public interface HelloWorld {
	
	String sayHello(String name);

}
