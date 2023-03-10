package org.gecko.playground.e4.rcp.parts;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.widgets.ButtonFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
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


	@PostConstruct
	public void createComposite(Composite parent) {
		this.parent = parent;
		parent.setLayout(new GridLayout(1, false));
		
		Composite searchLayout = new Composite(parent, SWT.FILL);
		searchLayout.setLayout(new GridLayout(2, false));

		Text searchField = new Text(searchLayout, SWT.BORDER);
		ButtonFactory.newButton(SWT.BORDER).text("Search").onSelect(evt -> {
//			TODO: actual search here!
			List<Person> searchResult = searchService.searchPersonsByFirstName(searchField.getText(), false);
			if(searchResult == null || searchResult.isEmpty()) {
				MessageDialog.openWarning(parent.getShell(), "No Result", "No Person matching your search criteria has been found!");
				return;
			}
			if(tableViewer != null) tableViewer.getTable().dispose();
			tableViewer = new TableViewer(parent);
			tableViewer.setLabelProvider(LabelProvider.createTextProvider(o -> {
				if(o instanceof Person) {
					Person p = (Person) o;
					return p.getFirstNames() + " " + p.getLastName();
				}
				return "N/A";
			}));
			tableViewer.setContentProvider(ArrayContentProvider.getInstance());
			tableViewer.setInput(searchResult);
			tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));		
			parent.redraw();
		}).create(searchLayout);
	}

	@Focus
	public void setFocus() {
		parent.setFocus();
	}
}