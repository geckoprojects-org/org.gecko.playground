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
import org.gecko.playground.model.person.Contact;
import org.gecko.playground.model.person.ContactType;
import org.gecko.playground.model.person.ContextType;
import org.gecko.playground.model.person.PersonPackage;

/**
 * 
 * @author ilenia
 * @since Mar 17, 2023
 */
public class ContactDialog extends AbstractDialog<Contact>{
	
	
	public ContactDialog(PersonPackage personPackage, Shell parent, int style) {
		createDialog(parent, style);
		
		object = personPackage.getPersonFactory().createContact();
		
		Combo ctxCombo = addComboWithLabel("Select Contact Context", dialog, SWT.BORDER);
		ctxCombo.setToolTipText("Select Contact Context");
		ctxCombo.setItems(ContextType.HOME.toString(), ContextType.WORK.toString(), ContextType.OTHER.toString());
		ctxCombo.addListener(SWT.Selection, evt -> {
			object.setContext(ContextType.getByName(ctxCombo.getItem(ctxCombo.getSelectionIndex())));
		});
		
		Combo typeCombo = addComboWithLabel("Select Contact Type", dialog, SWT.BORDER);
		typeCombo.setToolTipText("Select Contact Type");
		typeCombo.setItems(ContactType.EMAIL.toString(), ContactType.INSTAGRAM.toString(),
				ContactType.PHONE.toString(), ContactType.MOBILE.toString(),
				ContactType.LINKEDIN.toString(),ContactType.OTHER.toString());
		typeCombo.addListener(SWT.Selection, evt -> {
			object.setType(ContactType.getByName(ctxCombo.getItem(ctxCombo.getSelectionIndex())));
		});
		
		Text contactText = addTextWithLabel("Contact", dialog, SWT.BORDER);
		contactText.addListener(SWT.Modify, evt -> {
			object.setValue(contactText.getText());
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
		return "Add Contact";
	}

}
