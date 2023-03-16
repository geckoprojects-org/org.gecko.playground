package org.gecko.playground.vaadin.views.form;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.gecko.playground.model.person.Address;
import org.gecko.playground.model.person.Contact;
import org.gecko.playground.model.person.Person;
import org.gecko.playground.model.person.PersonPackage;
import org.gecko.playground.mongo.PersonPersistenceService;
import org.gecko.playground.search.PersonIndexService;
import org.gecko.playground.vaadin.helpers.AddressDialog;
import org.gecko.playground.vaadin.helpers.AddressGrid;
import org.gecko.playground.vaadin.helpers.ContactDialog;
import org.gecko.playground.vaadin.helpers.ContactGrid;
import org.gecko.playground.vaadin.views.main.MainView;
import org.gecko.vaadin.whiteboard.annotations.VaadinComponent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;
import org.osgi.service.component.annotations.ServiceScope;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "form", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Person Form")
@Component(name = "PersonFormView", service=PersonFormView.class, scope = ServiceScope.PROTOTYPE)
@VaadinComponent()
public class PersonFormView extends VerticalLayout {
	
	@Reference
	PersonPackage personPackage;
	
	@Reference
	PersonIndexService indexService;
	
	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	PersonPersistenceService personService;

	private static final long serialVersionUID = 6928570077895818105L;
	private List<Address> addresses = new ArrayList<>();
	private List<Contact> contacts = new ArrayList<>();
	
	@Activate
	public void renderView() {
		
		HorizontalLayout infoLayout = new HorizontalLayout();
		infoLayout.setAlignItems(Alignment.CENTER);		
		TextField firstName = new TextField("First Name");
		TextField lastName = new TextField("Last Name");
		DatePicker birthDate = new DatePicker("Birth Date");
		infoLayout.add(firstName, lastName, birthDate);

		HorizontalLayout dialogBtnLayout = new HorizontalLayout();
		dialogBtnLayout.setAlignItems(Alignment.CENTER);
		Button addAddressBtn = new Button("Add Address");
		Button addContactBtn = new Button("Add Contact");		
		dialogBtnLayout.add(addAddressBtn, addContactBtn);
		
		HorizontalLayout gridsLayout = new HorizontalLayout();
		gridsLayout.setSizeFull();
		gridsLayout.setVisible(false);
		AddressGrid addressGrid = new AddressGrid();
		ContactGrid contactGrid = new ContactGrid();		
		gridsLayout.add(addressGrid, contactGrid);
		
		HorizontalLayout btnLayout = new HorizontalLayout();
		Button saveBtn = new Button("Save");
		Button clearBtn = new Button("Clear");
		btnLayout.add(saveBtn, clearBtn);
		
		add(infoLayout, dialogBtnLayout, gridsLayout, btnLayout);
		
//		Listeners
		addAddressBtn.addClickListener(evt -> {
			AddressDialog addressDialog = new AddressDialog(personPackage);
			addressDialog.open();
			addressDialog.addOpenedChangeListener(evt2 -> {
				if(!evt2.getSource().isOpened()) {
					Address address = addressDialog.getAddress();
					if(address == null) return;
					addresses.add(address);					
					addressGrid.setItems(addresses);
					if(contactGrid.isVisible() || addressGrid.isVisible()) gridsLayout.setVisible(true);
				}
			});
		});
		
		addContactBtn.addClickListener(evt -> {
			ContactDialog contactDialog = new ContactDialog(personPackage);
			contactDialog.open();
			contactDialog.addOpenedChangeListener(evt2 -> {
				if(!evt2.getSource().isOpened()) {
					Contact contact = contactDialog.getContact();
					if(contact == null) return;
					contacts.add(contact);
					contactGrid.setItems(contacts);
					if(contactGrid.isVisible() || addressGrid.isVisible()) gridsLayout.setVisible(true);
				}
			});
		});
		
		saveBtn.addClickListener(evt -> {
			Person person = personPackage.getPersonFactory().createPerson();
			person.setId(UUID.randomUUID().toString());
			person.setFirstNames(firstName.getValue());
			person.setLastName(lastName.getValue());
			if(birthDate.getValue() != null) person.setBirthDate(Date.from(birthDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			person.getAddress().addAll(addressGrid.getItems());
			person.getContact().addAll(contactGrid.getItems());
			try {
				personService.savePerson(person);
				indexService.indexPerson(person, true);
				Notification.show("Person saved and indexed successfully!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			} catch(Exception e) {
				Notification.show("Something went wrong when saving Person object.").addThemeVariants(NotificationVariant.LUMO_ERROR);
				e.printStackTrace();
			}
		
		});
		
		clearBtn.addClickListener(evt -> {
			firstName.setValue("");
			lastName.setValue("");
			birthDate.setValue(LocalDate.now());
			addressGrid.setItems(Collections.emptyList());
			addressGrid.setVisible(false);
			contactGrid.setItems(Collections.emptyList());
			contactGrid.setVisible(false);
		});
	}

}
