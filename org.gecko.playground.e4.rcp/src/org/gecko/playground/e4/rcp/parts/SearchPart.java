package org.gecko.playground.e4.rcp.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.widgets.ButtonFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.gecko.eclipse.annotations.RequireEclipseCompatibility;
import org.gecko.eclipse.annotations.RequireEclipseConsole;
import org.gecko.eclipse.annotations.RequireEclipseE4;
import org.gecko.playground.model.person.Person;
import org.gecko.playground.search.PersonSearchService;
import org.osgi.service.component.annotations.Component;

@RequireEclipseConsole
@RequireEclipseE4
@RequireEclipseCompatibility
@Component(name = "SearchPart", service=SearchPart.class)
public class SearchPart {

	@Inject
	PersonSearchService searchService;

	@Inject
	MPart part;

	private TableViewer tableViewer;
	private Composite parent;
	private String searchOption;
	
	private static final String SEARCH_OPTION_BY_FIRST_NAME = "First name";
	private static final String SEARCH_OPTION_BY_LAST_NAME = "Last name";
	private static final String SEARCH_OPTION_BY_BOTH = "Both";

	@PostConstruct
	public void createComposite(Composite parent) {

		this.parent = parent;
		parent.setLayout(new GridLayout(1, false));

		Composite searchOptionsLayout = new Composite(parent, SWT.NONE);
		searchOptionsLayout.setLayout(new GridLayout(2, false));
		
		Group searchOptions = new Group(searchOptionsLayout, SWT.SHADOW_IN);
		searchOptions.setText("Search by");
		searchOptions.setLayout(new RowLayout(SWT.VERTICAL));
		
		Button searchByFirstNameBtn = new Button(searchOptions, SWT.RADIO);
		searchByFirstNameBtn.setText(SEARCH_OPTION_BY_FIRST_NAME);
		
		Button searchByLastNameBtn = new Button(searchOptions, SWT.RADIO);
		searchByLastNameBtn.setText(SEARCH_OPTION_BY_LAST_NAME);
		
		
		Button searchByBothBtn = new Button(searchOptions, SWT.RADIO);
		searchByBothBtn.setText(SEARCH_OPTION_BY_BOTH);
		
		Button exactMatchBtn = new Button(searchOptionsLayout, SWT.CHECK);
		exactMatchBtn.setVisible(false);
		exactMatchBtn.setText("Exact Match?");
		
		searchByFirstNameBtn.addListener(SWT.Selection, evt -> {
			searchOption = SEARCH_OPTION_BY_FIRST_NAME;
			exactMatchBtn.setVisible(true);
			exactMatchBtn.setSelection(false);
		});
		
		searchByLastNameBtn.addListener(SWT.Selection, evt -> {
			searchOption = SEARCH_OPTION_BY_LAST_NAME;
			exactMatchBtn.setVisible(true);
			exactMatchBtn.setSelection(false);
		});
		
		searchByBothBtn.addListener(SWT.Selection, evt -> {
			searchOption = SEARCH_OPTION_BY_BOTH;
			exactMatchBtn.setVisible(false);
			exactMatchBtn.setSelection(false);
		});
		

		Composite searchLayout = new Composite(parent, SWT.FILL);
		searchLayout.setLayout(new GridLayout(3, false));

		Text searchField = new Text(searchLayout, SWT.BORDER);
		ButtonFactory.newButton(SWT.BORDER).text("Search").onSelect(evt -> {
			List<Person> searchResult = new ArrayList<>();
			switch(searchOption) {
			case SEARCH_OPTION_BY_FIRST_NAME:
				searchResult = searchService.searchPersonsByFirstName(searchField.getText(), exactMatchBtn.getSelection());
				break;
			case SEARCH_OPTION_BY_LAST_NAME:
				searchResult = searchService.searchPersonsByLastName(searchField.getText(), exactMatchBtn.getSelection());
				break;
			case SEARCH_OPTION_BY_BOTH:
				searchResult = searchService.searchPersonsName(searchField.getText());
				break;
			}
			if(searchResult == null || searchResult.isEmpty()) {
				MessageDialog.openWarning(parent.getShell(), "No Result", "No Person matching your search criteria has been found!");
				if(tableViewer != null) {
					tableViewer.setInput(Collections.emptyList());
					tableViewer.getTable().setVisible(false);
				}
				return;
			}			
			tableViewer.setInput(searchResult);
			tableViewer.getTable().setVisible(true);
			tableViewer.getTable().forceFocus();
		}).create(searchLayout);
		
		ButtonFactory.newButton(SWT.PUSH).text("Clear").onSelect(evt -> {
			if(tableViewer != null) {
				tableViewer.setInput(Collections.emptyList());
				tableViewer.getTable().setVisible(false);
			}
			exactMatchBtn.setSelection(false);
			exactMatchBtn.setVisible(false);
			searchByFirstNameBtn.setSelection(false);
			searchByLastNameBtn.setSelection(false);
			searchByBothBtn.setSelection(false);
			searchField.setText("");
		}).create(searchLayout);

		tableViewer = new TableViewer(parent);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(Collections.emptyList());
		tableViewer.getTable().setHeaderVisible(true);
		TableColumn firstNameColumn = new TableColumn(tableViewer.getTable(), SWT.LEFT, 0);
		firstNameColumn.setText("First Name");
		firstNameColumn.setWidth(400);
		TableColumn lastNameColumn = new TableColumn(tableViewer.getTable(), SWT.LEFT, 1);
		lastNameColumn.setText("Last Name");
		lastNameColumn.setWidth(400);
		TableColumn birthDateColumn = new TableColumn(tableViewer.getTable(), SWT.LEFT, 2);
		birthDateColumn.setText("Birth Date");
		birthDateColumn.setWidth(400);
		tableViewer.setLabelProvider(new PersonTableLabelProvider());
		tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));		
		tableViewer.getTable().setVisible(false);
	}

	@Focus
	public void setFocus() {
		parent.setFocus();
	}
}