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

import java.util.Collections;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

/**
 * 
 * @author ilenia
 * @since Mar 17, 2023
 */
public abstract class AbstractTableViewer<T> {

	private TableViewer tableViewer;
	
	protected abstract ITableLabelProvider getLabelProvider();
	
	protected void createTableViewer(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.BORDER);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(Collections.emptyList());
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.setLabelProvider(getLabelProvider());
		tableViewer.getTable().setLayout(new GridLayout());
		tableViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tableViewer.getTable().setVisible(false);
		tableViewer.getTable().pack();
	}
	
	public void setInput(Object input) {
		tableViewer.setInput(input);
		if(tableViewer.getTable().getItems().length > 0) {
			tableViewer.getTable().setVisible(true);
			resizeTable();
		} else {
			tableViewer.getTable().setVisible(false);
		}
		tableViewer.refresh();
		tableViewer.getTable().redraw();
	}
	
	private void resizeTable() {
		for(TableColumn column : tableViewer.getTable().getColumns()) {
			column.pack();
		}
	}
	
	protected void addTableColumn(String text, int style, int index, int initialWidth) {
		TableColumn column = new TableColumn(tableViewer.getTable(), style, index);
		column.setText(text);
		column.setWidth(initialWidth);
	}
	
	public TableViewer getTableViewer() {
		return tableViewer;
	}
}
