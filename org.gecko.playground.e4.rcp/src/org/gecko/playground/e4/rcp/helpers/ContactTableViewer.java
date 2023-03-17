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
import org.gecko.playground.model.person.Contact;

/**
 * 
 * @author ilenia
 * @since Mar 17, 2023
 */
public class ContactTableViewer extends AbstractTableViewer<Contact> {


	public ContactTableViewer(Composite parent) {
		createTableViewer(parent);
		addTableColumn("Context", SWT.LEFT, 0, 100);
		addTableColumn("Type", SWT.LEFT, 1, 100);
		addTableColumn("Contact", SWT.LEFT, 2, 100);		
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.e4.rcp.parts.AbstractTableViewer#getLabelProvider()
	 */
	@Override
	protected ITableLabelProvider getLabelProvider() {
		return new ContactTableLabelProvider();
	}

}
