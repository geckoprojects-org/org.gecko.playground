/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.playground.vaadin.helpers;

import java.text.SimpleDateFormat;
import java.util.Collection;

import org.gecko.playground.model.person.Person;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;

/**
 * 
 * @author ilenia
 * @since Mar 10, 2023
 */
public class PersonGrid extends Grid<Person>{

	/** serialVersionUID */
	private static final long serialVersionUID = 6662887168435220121L;
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	
	public PersonGrid() {
		addColumn(Person::getFirstNames).setHeader("First Names").setAutoWidth(true);
		addColumn(Person::getLastName).setHeader("Last Name").setAutoWidth(true);
		addComponentColumn(person -> {
			if(person.getBirthDate() == null) {
				return new Label("N/A");
			} 
			return new Label(DATE_FORMAT.format(person.getBirthDate()));
		}).setHeader("Birth Date").setAutoWidth(true);
		setItemDetailsRenderer(new ComponentRenderer<HorizontalLayout,Person>(HorizontalLayout::new, (layout, person) -> {
			AddressGrid addressGrid = new AddressGrid();
			addressGrid.setItems(person.getAddress());			
			ContactGrid contactGrid = new ContactGrid();
			contactGrid.setItems(person.getContact());
			layout.add(addressGrid, contactGrid);
		}));
	}
	
	@Override
	public GridListDataView<Person> setItems(Collection<Person> items) {
		if(items == null || items.isEmpty()) {
			setVisible(false);
		}
		else {
			setVisible(true);
		}
		return super.setItems(items);
	}

}
