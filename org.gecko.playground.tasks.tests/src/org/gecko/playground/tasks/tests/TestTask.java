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
package org.gecko.playground.tasks.tests;

import org.gecko.playground.tasks.AbstractTask;

/**
 * 
 * @author mark
 * @since 29.04.2022
 */
public class TestTask extends AbstractTask {
	
	private final String id;
	private final String name;

	public TestTask(String id, String name) {
		this.id = id;
		this.name = name;
		
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.tasks.AbstractTask#doActivate()
	 */
	@Override
	protected void doActivate() {
		System.out.println("Foo Activate");
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.tasks.AbstractTask#doDeactivate()
	 */
	@Override
	protected void doDeactivate() {
		System.out.println("Foo Deactivate");
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.tasks.TaskInfo#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.tasks.TaskInfo#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

}
