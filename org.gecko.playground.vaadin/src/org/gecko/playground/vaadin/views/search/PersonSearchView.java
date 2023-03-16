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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gecko.playground.model.person.Person;
import org.gecko.playground.search.PersonSearchService;
import org.gecko.playground.vaadin.helpers.PersonGrid;
import org.gecko.playground.vaadin.views.main.MainView;
import org.gecko.vaadin.whiteboard.annotations.VaadinComponent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

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
	
	@Reference
	PersonSearchService searchService;

	/** serialVersionUID */
	private static final long serialVersionUID = 862517653358457467L;
	
	private static final String OPTION_FIRST_NAME = "First name";
	private static final String OPTION_LAST_NAME = "Last name";
	private static final String OPTION_COMPOSITE = "Both";

	
	@Activate
	public void renderView() {
		
		HorizontalLayout optionsLayout = new HorizontalLayout();
		optionsLayout.setSizeFull();
		optionsLayout.setAlignItems(Alignment.BASELINE);
		
		RadioButtonGroup<String> optionsGroup = new RadioButtonGroup<>();
		optionsGroup.setLabel("Search by");
		optionsGroup.setItems(OPTION_FIRST_NAME, OPTION_LAST_NAME, OPTION_COMPOSITE);
		optionsGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
		
		Checkbox exactMatchBox = new Checkbox("Exact match?");
		exactMatchBox.setVisible(false);
		optionsLayout.add(optionsGroup, exactMatchBox);
		
		HorizontalLayout searchLayout = new HorizontalLayout();
		searchLayout.setSizeFull();
		searchLayout.setAlignItems(Alignment.BASELINE);
		TextField searchField = new TextField("Search");
		Button searchBtn = new Button(new Icon(VaadinIcon.SEARCH));
		searchBtn.setEnabled(false);
		Button clearBtn = new Button(new Icon(VaadinIcon.ERASER));
		
		PersonGrid personGrid = new PersonGrid();
		personGrid.setVisible(false);
		
		searchLayout.add(searchField, searchBtn, clearBtn);
		
		add(optionsLayout, searchLayout, personGrid);
		
//		Listeners
		optionsGroup.addValueChangeListener(evt -> {
			if(evt.getValue() == null) {
				exactMatchBox.setVisible(false);
				searchBtn.setEnabled(false);
				return;
			}
			switch(evt.getValue()) {
			case OPTION_FIRST_NAME: case OPTION_LAST_NAME:
				exactMatchBox.setVisible(true);
				searchBtn.setEnabled(true);
				return;
			case OPTION_COMPOSITE: default:
				exactMatchBox.setVisible(false);
				searchBtn.setEnabled(true);
				return;
			}
		});
		
		searchBtn.addClickListener(evt -> {
			List<Person> result = new ArrayList<>();
			String query = searchField.getValue();
			switch(optionsGroup.getValue()) {
			case OPTION_FIRST_NAME:
				result = searchService.searchPersonsByFirstName(query, exactMatchBox.getValue());
				break;
			case OPTION_LAST_NAME:
				result = searchService.searchPersonsByLastName(query, exactMatchBox.getValue());
				break;
			case OPTION_COMPOSITE:
				result = searchService.searchPersonsName(query);
				break;
			}
			if(result == null || result.isEmpty()) {
				Notification.show("No Person object matching your search criteria has been found.").addThemeVariants(NotificationVariant.LUMO_CONTRAST);
				return;
			}
			personGrid.setItems(result);			
		});
		
		clearBtn.addClickListener(evt -> {
			searchBtn.setEnabled(false);
			searchField.setValue("");
			optionsGroup.setValue(null);
			exactMatchBox.setValue(false);
			personGrid.setItems(Collections.emptyList());
		});
	}

}
