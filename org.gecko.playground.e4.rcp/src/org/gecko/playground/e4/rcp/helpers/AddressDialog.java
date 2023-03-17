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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.gecko.playground.model.person.Address;
import org.gecko.playground.model.person.ContextType;
import org.gecko.playground.model.person.PersonPackage;

/**
 * 
 * @author ilenia
 * @since Mar 17, 2023
 */
public class AddressDialog extends AbstractDialog<Address>{
	
	
	public AddressDialog(PersonPackage personPackage, Shell parent, int style) {

		createDialog(parent, style);
		object = personPackage.getPersonFactory().createAddress();
		
		Combo ctxCombo = addComboWithLabel("Select Context", dialog, SWT.BORDER);		
		ctxCombo.setToolTipText("Select Address Context");
		ctxCombo.setItems(ContextType.HOME.toString(), ContextType.WORK.toString(), ContextType.OTHER.toString());
		ctxCombo.addListener(SWT.Selection, evt -> {
			object.setContext(ContextType.getByName(ctxCombo.getItem(ctxCombo.getSelectionIndex())));
		});
		
		Text street = addTextWithLabel("Street", dialog, SWT.BORDER);
		street.addListener(SWT.Modify, evt -> {
			object.setStreet(street.getText());
		});
		Text zip = addTextWithLabel("Zip Code", dialog, SWT.BORDER);
		zip.addListener(SWT.Modify, evt -> {
			object.setZip(zip.getText());
		});
		Text city = addTextWithLabel("City", dialog, SWT.BORDER);
		city.addListener(SWT.Modify, evt -> {
			object.setCity(city.getText());
		});
		addOKButton();
		dialog.pack();
	}
	
	

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.e4.rcp.parts.AbstractDialog#setDialogTitle()
	 */
	@Override
	protected String setDialogTitle() {
		return "Add Address";
	}

}
