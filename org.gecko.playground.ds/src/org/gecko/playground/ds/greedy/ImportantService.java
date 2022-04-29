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
package org.gecko.playground.ds.greedy;

import org.osgi.service.component.annotations.Component;

/**
 * 
 * @author mark
 * @since 29.04.2022
 */
@Component(name="IS")
public class ImportantService implements Important{

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.ds.greedy.Important#important()
	 */
	@Override
	public boolean important() {
		return true;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.ds.greedy.Important#very()
	 */
	@Override
	public boolean very() {
		return false;
	}

}
