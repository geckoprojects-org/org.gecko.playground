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

	@Inject
	private MPart part;
	
	@Inject
	private MWindow window;	

	private Composite parent;
	private Text firstName, lastName;
	private DateTime birthDateField;



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

		Composite btnComposite = new Composite(parent, SWT.FILL);
		btnComposite.setLayout(new GridLayout(2, true));
		ButtonFactory.newButton(SWT.BORDER).text("Save").onSelect(evt -> {
			save();
		}).create(btnComposite);

		ButtonFactory.newButton(SWT.BORDER).text("Clear").onSelect(evt -> {
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
		}
	}

	private void doSave() {
		Person person = personPackage.getPersonFactory().createPerson();
		person.setId(UUID.randomUUID().toString());
		person.setFirstNames(firstName.getText());
		person.setLastName(lastName.getText());
		person.setBirthDate(Date.from(LocalDate.of(birthDateField.getYear(), birthDateField.getMonth()+1, birthDateField.getDay()).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		indexService.indexPerson(person, true);
		MessageDialog.openInformation(parent.getShell(), "Person Saved", "Person Added to the Index!");
	}
}