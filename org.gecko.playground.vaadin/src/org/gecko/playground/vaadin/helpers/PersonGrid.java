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

import org.gecko.playground.model.person.Person;

import com.vaadin.flow.component.grid.Grid;
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
	
	public PersonGrid() {
		addColumn(Person::getFirstNames).setHeader("First Names").setAutoWidth(true);
		addColumn(Person::getLastName).setHeader("Last Name").setAutoWidth(true);
		addColumn(Person::getBirthDate).setHeader("Birth Date").setAutoWidth(true);
		setItemDetailsRenderer(new ComponentRenderer<HorizontalLayout,Person>(HorizontalLayout::new, (layout, person) -> {
			AddressGrid addressGrid = new AddressGrid();
			addressGrid.setItems(person.getAddress());			
			ContactGrid contactGrid = new ContactGrid();
			contactGrid.setItems(person.getContact());
			layout.add(addressGrid, contactGrid);
		}));
	}

}
