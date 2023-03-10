package org.gecko.playground.e4.rcp.parts;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.widgets.ButtonFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.gecko.eclipse.annotations.RequireEclipseCompatibility;
import org.gecko.eclipse.annotations.RequireEclipseConsole;
import org.gecko.eclipse.annotations.RequireEclipseE4;
import org.gecko.playground.model.person.Person;
import org.gecko.playground.model.person.PersonPackage;
import org.gecko.playground.search.PersonIndexService;
import org.osgi.service.component.annotations.Component;

@RequireEclipseConsole
@RequireEclipseE4
@RequireEclipseCompatibility
@Component(name = "PersonFormPart", service = PersonFormPart.class)
public class PersonFormPart {
	
	@Inject
	PersonPackage personPackage;
	
	@Inject
	PersonIndexService indexService;

	private Composite parent;

	@Inject
	private MPart part;

	@PostConstruct
	public void createComposite(Composite parent) {
		this.parent = parent;
		parent.setLayout(new GridLayout(1, false));
		
		Composite infoComposite = new Composite(parent, SWT.BORDER | SWT.FILL);		
		infoComposite.setLayout(new GridLayout(6, false));

		Label firstNameLabel = new Label(infoComposite, SWT.NONE);
		firstNameLabel.setText("First Name:");
		Text firstName = new Text(infoComposite, SWT.BORDER);
		firstName.setToolTipText("Enter the Person first name");
		
		Label lastNameLabel = new Label(infoComposite, SWT.NONE);
		lastNameLabel.setText("Last Name:");
		Text lastName = new Text(infoComposite, SWT.BORDER);
		lastName.setToolTipText("Enter the Person last name");
		
		Label birthDateLabel = new Label(infoComposite, SWT.NONE);
		birthDateLabel.setText("Birth Date:");
		DateTime birthDate = new DateTime(infoComposite, SWT.CALENDAR);
		
		ButtonFactory.newButton(SWT.BORDER).text("Save").onSelect(evt -> {
			Person person = personPackage.getPersonFactory().createPerson();
			person.setId(UUID.randomUUID().toString());
			person.setFirstNames(firstName.getText());
			person.setLastName(lastName.getText());
			person.setBirthDate(Date.from(LocalDate.of(birthDate.getYear(), birthDate.getMonth(), birthDate.getDay()).atStartOfDay(ZoneId.systemDefault()).toInstant()));
			indexService.indexPerson(person, true);
			MessageDialog.openInformation(parent.getShell(), "Person Saved", "Person Added to the Index!");
		}).create(parent);
		
	}

	@Focus
	public void setFocus() {
		parent.setFocus();
	}

	@Persist
	public void save() {
		part.setDirty(false);
	}
}