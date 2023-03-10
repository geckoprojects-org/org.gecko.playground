package org.gecko.playground.vaadin.helpers;

import org.gecko.playground.model.person.Address;
import org.gecko.playground.model.person.ContextType;
import org.gecko.playground.model.person.PersonPackage;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class AddressDialog extends Dialog {

	private static final long serialVersionUID = 2678071019842640431L;
	private Address address;
	
	public AddressDialog(PersonPackage personPackage) {		
		setHeaderTitle("Add Address");
		
		VerticalLayout dialogLayout = new VerticalLayout();
		dialogLayout.setSizeFull();
		
		ComboBox<ContextType> ctxComboBox = new ComboBox<>();
		ctxComboBox.setHelperText("Select address context");
		ctxComboBox.setItems(ContextType.VALUES);
		
		HorizontalLayout hl = new HorizontalLayout();
		TextField street = new TextField("Street");
		TextField zip = new TextField("Zip");
		TextField city = new TextField("City");
		
		hl.add(street, zip, city);
		dialogLayout.add(ctxComboBox, hl);
		add(dialogLayout);
		
		Button saveBtn = new Button("Save", evt -> {
			address = personPackage.getPersonFactory().createAddress();
			address.setContext(ctxComboBox.getValue());
			address.setStreet(street.getValue());
			address.setZip(zip.getValue());
			address.setCity(city.getValue());
			close();
		});
		Button closeBtn = new Button("Close", evt -> close());
		getFooter().add(saveBtn, closeBtn);		
	}
	
	public Address getAddress() {
		return address;
	}

}
