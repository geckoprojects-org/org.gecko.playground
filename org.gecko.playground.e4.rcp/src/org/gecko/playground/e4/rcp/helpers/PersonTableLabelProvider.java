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

import java.text.SimpleDateFormat;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.gecko.playground.model.person.Address;
import org.gecko.playground.model.person.Contact;
import org.gecko.playground.model.person.Person;

/**
 * 
 * @author ilenia
 * @since Mar 13, 2023
 */
public class PersonTableLabelProvider implements ITableLabelProvider {
	
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	@Override
	public String getColumnText(Object element, int columnIndex) {
		String result = "";
		Person person = (Person) element;
		switch(columnIndex) {
		case 0: 
			result =  person.getFirstNames();
			break;
		case 1:
			result = person.getLastName();
			break;
		case 2:
			result = DATE_FORMAT.format(person.getBirthDate());
			break;
		case 3:
			result = createAddressesList(person);
			break;
		case 4:
			result = createContactsList(person);
			break;
		}
		return result;
	}

	private String createContactsList(Person person) {
		String result = "";
		if(person.getContact().isEmpty()) {
			result = "N/A";
		} else {
			for(Contact contact : person.getContact()) {
				if(contact.getValue() == null) continue;
				StringBuilder formattedContact = new StringBuilder();
				formattedContact.append(contact.getContext().toString().concat(", "));
				formattedContact.append(contact.getType().toString().concat(", "));
				formattedContact.append(contact.getValue().concat("\n"));
				String formattedContactStr = formattedContact.toString();
				result += formattedContactStr;
			}
			result = result.substring(0, result.length()-1);				
		}
		return result;
	}
	
	private String createAddressesList(Person person) {
		String result = "";
		if(person.getAddress().isEmpty()) {
			result = "N/A";
		} else {
			for(Address address : person.getAddress()) {
				StringBuilder formattedAddress = new StringBuilder();
				formattedAddress.append(address.getStreet() != null ? address.getStreet().concat(", ") : "");
				formattedAddress.append(address.getZip() != null ? address.getZip().concat(", ") : "");
				formattedAddress.append(address.getCity() != null ? address.getCity() : "");
				String formattedAddressStr = formattedAddress.toString();
				if(formattedAddressStr.endsWith(", ")) formattedAddressStr = formattedAddressStr.substring(0, formattedAddressStr.length()-2);
				formattedAddressStr += "\n";
				result += formattedAddressStr;
			}
			result = result.substring(0, result.length()-1);				
		}
		return result;
	}
}
