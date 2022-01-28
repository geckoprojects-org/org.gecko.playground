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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.condition.Condition;

/**
 * 
 * @author mark
 * @since 28.01.2022
 */
@Component(reference = @Reference(name = "cond", target = "(database=true)", service = Condition.class))
public class DatabaseAvailableChecker {
	
	@Activate
	public void activate() {
		System.out.println("Database seems available: Doing sthg with the database ...");
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("Database has gone! Stop working with database!");
	}

}
