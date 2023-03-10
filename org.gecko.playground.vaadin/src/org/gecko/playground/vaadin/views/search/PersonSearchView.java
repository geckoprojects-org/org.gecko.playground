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
package org.gecko.playground.vaadin.views.search;

import org.gecko.playground.vaadin.views.main.MainView;
import org.gecko.vaadin.whiteboard.annotations.VaadinComponent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

/**
 * 
 * @author ilenia
 * @since Mar 10, 2023
 */
@Route(value = "search", layout = MainView.class)
@PageTitle("Search")
@Component(name = "PersonSearchView", service=PersonSearchView.class, scope = ServiceScope.PROTOTYPE)
@VaadinComponent()
public class PersonSearchView extends VerticalLayout {

	/** serialVersionUID */
	private static final long serialVersionUID = 862517653358457467L;
	
	private static final String OPTION_FIRST_NAME = "Search by first name";
	private static final String OPTION_LAST_NAME = "Search by last name";
	private static final String OPTION_COMPOSITE = "Composite search";

	
	@Activate
	public void renderView() {
		
		VerticalLayout optionsLayout = new VerticalLayout();
		optionsLayout.setSizeFull();
		RadioButtonGroup<String> optionsGroup = new RadioButtonGroup<>();
		optionsGroup.setItems(OPTION_FIRST_NAME, OPTION_LAST_NAME, OPTION_COMPOSITE);
		optionsLayout.add(optionsGroup);
		
		
		
		add(optionsLayout);
	}

}
