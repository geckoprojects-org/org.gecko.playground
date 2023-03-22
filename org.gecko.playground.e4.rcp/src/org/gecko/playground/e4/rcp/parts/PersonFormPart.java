package org.gecko.playground.e4.rcp.parts;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.ISaveHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.widgets.ButtonFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.gecko.eclipse.annotations.RequireEclipseCompatibility;
import org.gecko.eclipse.annotations.RequireEclipseConsole;
import org.gecko.eclipse.annotations.RequireEclipseE4;
import org.gecko.playground.e4.rcp.handlers.ExitFormHandler;
import org.gecko.playground.e4.rcp.helpers.AddressDialog;
import org.gecko.playground.e4.rcp.helpers.AddressTableViewer;
import org.gecko.playground.e4.rcp.helpers.ContactDialog;
import org.gecko.playground.e4.rcp.helpers.ContactTableViewer;
import org.gecko.playground.model.person.Address;
import org.gecko.playground.model.person.Contact;
import org.gecko.playground.model.person.Person;
import org.gecko.playground.model.person.PersonPackage;
import org.gecko.playground.mongo.PersonPersistenceService;
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
	
	@Inject
	PersonPersistenceService personPersistenceService;

	@Inject
	private MPart part;
	
	@Inject
	private MWindow window;	

	private Composite parent;
	private Text firstName, lastName;
	private DateTime birthDateField;
	
	private List<Address> addresses = new ArrayList<>();
	private List<Contact> contacts = new ArrayList<>();



	@PostConstruct
	public void createComposite(Composite parent) {
		
		ISaveHandler saveHandler = new ExitFormHandler();
		window.getContext().set(ISaveHandler.class, saveHandler);
		
		this.parent = parent;
		parent.setLayout(new GridLayout(1, false));

		Composite infoComposite = new Composite(parent, SWT.BORDER | SWT.FILL);		
		infoComposite.setLayout(new GridLayout(7, false));

		Label firstNameLabel = new Label(infoComposite, SWT.NONE);
		firstNameLabel.setText("First Name:");
		firstName = new Text(infoComposite, SWT.BORDER);
		firstName.setToolTipText("Enter the Person first name");
		firstName.addListener(SWT.Modify, evt -> part.setDirty(true));

		Label lastNameLabel = new Label(infoComposite, SWT.NONE);
		lastNameLabel.setText("Last Name:");
		lastName = new Text(infoComposite, SWT.BORDER);
		lastName.setToolTipText("Enter the Person last name");
		lastName.addListener(SWT.Modify, evt -> part.setDirty(true));

		Label birthDateLabel = new Label(infoComposite, SWT.NONE);
		birthDateLabel.setText("Birth Date:");
		birthDateField = new DateTime(infoComposite, SWT.DATE);

		ButtonFactory.newButton(SWT.PUSH).text("Select Date").onSelect(evt -> {
			Shell dialog = new Shell(parent.getShell(), SWT.DIALOG_TRIM);
			dialog.setText("Select Date");
			dialog.setLayout(new GridLayout(1, false));
			DateTime birthDate = new DateTime(dialog, SWT.CALENDAR);
			Button okBtn = new Button(dialog, SWT.PUSH);
			okBtn.setText("OK");
			okBtn.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false ) );
			okBtn.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected( SelectionEvent e ) {
					birthDateField.setDate(birthDate.getYear(), birthDate.getMonth(), birthDate.getDay());
					part.setDirty(true);
					dialog.close();
				}
			});
			dialog.setDefaultButton(okBtn);
			dialog.pack();
			dialog.open();
		}).create(infoComposite);
		
		Composite refComposite = new Composite(parent, SWT.FILL);
		refComposite.setLayout(new GridLayout(2, true));
		
		Composite tablesComposite = new Composite(parent, SWT.FILL);
		tablesComposite.setLayout(new GridLayout(2, true));
		tablesComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		AddressTableViewer addressTable = new AddressTableViewer(tablesComposite);
		ContactTableViewer contactTable = new ContactTableViewer(tablesComposite);
		
		ButtonFactory.newButton(SWT.PUSH).text("Add Address").onSelect(evt -> {
			AddressDialog addDialog = new AddressDialog(personPackage, parent.getShell(), SWT.DIALOG_TRIM);
			addDialog.getDialog().getDefaultButton().addSelectionListener(new SelectionAdapter() {
				public void widgetSelected( SelectionEvent e ) {
					addresses.add(addDialog.getObject());
					addressTable.setInput(addresses);
					addDialog.getDialog().close();
				}
			});
			addDialog.getDialog().open();
		}).create(refComposite);
		
		ButtonFactory.newButton(SWT.PUSH).text("Add Contact").onSelect(evt -> {
			ContactDialog contactDialog = new ContactDialog(personPackage, parent.getShell(), SWT.DIALOG_TRIM);
			contactDialog.getDialog().getDefaultButton().addSelectionListener(new SelectionAdapter() {
				public void widgetSelected( SelectionEvent e ) {
					contacts.add(contactDialog.getObject());
					contactTable.setInput(contacts);
					contactDialog.getDialog().close();
				}
			});
			contactDialog.getDialog().open();
		}).create(refComposite);

		Composite btnComposite = new Composite(parent, SWT.FILL);
		btnComposite.setLayout(new GridLayout(2, true));
		ButtonFactory.newButton(SWT.BORDER).text("Save").onSelect(evt -> {
			save();
		}).create(btnComposite);

		ButtonFactory.newButton(SWT.BORDER).text("Clear").onSelect(evt -> {
			contactTable.setInput(Collections.emptyList());
			addressTable.setInput(Collections.emptyList());
			contacts.clear();
			addresses.clear();
			firstName.setText("");
			lastName.setText("");
			birthDateField.setDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
		}).create(btnComposite);

	}

	@Focus
	public void setFocus() {
		parent.setFocus();
	}

	@Persist
	public void save() {
		doSave();
		part.setDirty(false);
	}
	
	public void exit() {
		if(part.isDirty()) {
			if(MessageDialog.openQuestion(parent.getShell(), "Save Before Closing", "Do you want to save the data before closing?")) {
				save();
			}	
			else {
				part.setDirty(false);
			}
		}
	}

	private void doSave() {
		Person person = personPackage.getPersonFactory().createPerson();
		person.setId(UUID.randomUUID().toString());
		person.setFirstNames(firstName.getText());
		person.setLastName(lastName.getText());
		person.setBirthDate(Date.from(LocalDate.of(birthDateField.getYear(), birthDateField.getMonth()+1, birthDateField.getDay()).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		person.getContact().addAll(contacts);
		person.getAddress().addAll(addresses);
		indexService.indexPerson(person, true);
		personPersistenceService.savePerson(person);
		MessageDialog.openInformation(parent.getShell(), "Person Saved", "Person successfully added to the Index and to the DB!");
	}
}