package org.gecko.playground.vaadin.helpers;

import org.gecko.playground.model.person.Contact;
import org.gecko.playground.model.person.ContactType;
import org.gecko.playground.model.person.ContextType;
import org.gecko.playground.model.person.PersonPackage;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ContactDialog extends Dialog {

	private static final long serialVersionUID = -4391977371493665376L;
	private Contact contact;
	
	public ContactDialog(PersonPackage personPackage) {
		setHeaderTitle("Add Contact");
		VerticalLayout dialogLayout = new VerticalLayout();
		
		ComboBox<ContactType> typeComboBox = new ComboBox<>();
		typeComboBox.setHelperText("Select contact type");
		typeComboBox.setItems(ContactType.VALUES);
		
		ComboBox<ContextType> ctxComboBox = new ComboBox<>();
		ctxComboBox.setHelperText("Select contact context");
		ctxComboBox.setItems(ContextType.VALUES);
		
		TextField valueField = new TextField("Contact Value");		
		dialogLayout.add(typeComboBox, ctxComboBox, valueField);
		add(dialogLayout);
		
		Button saveBtn = new Button("Save", evt -> {
			contact = personPackage.getPersonFactory().createContact();
			contact.setType(typeComboBox.getValue());
			contact.setContext(ctxComboBox.getValue());
			contact.setValue(valueField.getValue());
			close();
		});
		Button closeBtn = new Button("Close", evt -> close());
		getFooter().add(saveBtn, closeBtn);
	}
	
	public Contact getContact() {
		return contact;
	}

}
