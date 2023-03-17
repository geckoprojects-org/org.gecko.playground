/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.playground.e4.rcp.helpers;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.gecko.playground.model.person.Person;

/**
 * 
 * @author ilenia
 * @since Mar 17, 2023
 */
public class PersonTableViewer extends AbstractTableViewer<Person> {
	
	public PersonTableViewer(Composite parent) {
		createTableViewer(parent);
		addTableColumn("First Name", SWT.LEFT, 0, 100);
		addTableColumn("Last Name", SWT.LEFT, 1, 100);
		addTableColumn("Birth Date", SWT.LEFT, 2, 100);
		addTableColumn("Addresses", SWT.LEFT, 3, 100);
		addTableColumn("Contacts", SWT.LEFT, 4, 100);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.e4.rcp.helpers.AbstractTableViewer#getLabelProvider()
	 */
	@Override
	protected ITableLabelProvider getLabelProvider() {
		return new PersonTableLabelProvider();
	}

}
