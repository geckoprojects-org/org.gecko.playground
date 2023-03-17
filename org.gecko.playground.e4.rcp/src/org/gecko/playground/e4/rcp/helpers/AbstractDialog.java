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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * 
 * @author ilenia
 * @since Mar 17, 2023
 */
public abstract class AbstractDialog<T> {
	
	public Shell dialog;
	public T object;
	
	protected abstract String setDialogTitle();
	
	protected void createDialog(Shell parent, int style) {
		dialog = new Shell(parent, style);
		dialog.setText(setDialogTitle());
		dialog.setLayout(new GridLayout(1, false));
	}
	
	protected Combo addComboWithLabel(String labelText, Composite parent, int style) {
		Composite composite = new Composite(parent, SWT.FILL);
		composite.setLayout(new GridLayout(2, false));
		Label label = new Label(composite, SWT.NONE);
		label.setText(labelText);
		Combo combo = new Combo(composite, style);
		return combo;
	}
	
	protected Text addTextWithLabel(String labelText, Composite parent, int style) {
		Composite composite = new Composite(parent, SWT.FILL);
		composite.setLayout(new GridLayout(2, true));
		Label label = new Label(composite, SWT.NONE);
		label.setText(labelText);
		Text text = new Text(composite, style);
		return text;
	}
	
	protected void addOKButton() {
		Button okBtn = new Button(dialog, SWT.PUSH);
		okBtn.setText("OK");
		okBtn.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false ) );
		dialog.setDefaultButton(okBtn);
	}
	
	public T getObject() {
		return object;
	}
	
	public Shell getDialog() {
		return dialog;
	}

}
