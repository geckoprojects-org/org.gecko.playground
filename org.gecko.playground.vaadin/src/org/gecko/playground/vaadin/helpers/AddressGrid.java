package org.gecko.playground.vaadin.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.gecko.playground.model.person.Address;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class AddressGrid extends Grid<Address> {

	private static final long serialVersionUID = 4368479694712968770L;
	private List<Address> addresses = new ArrayList<>();
	
	public AddressGrid() {
		addComponentColumn(address -> {
			Icon icon;
			switch(address.getContext()) {
			case HOME:
				icon = new Icon(VaadinIcon.HOME);
				break;
			case WORK:
				icon = new Icon(VaadinIcon.WORKPLACE);
				break;
			default: case OTHER:
				icon = new Icon(VaadinIcon.OFFICE);
				break;		
			}
			return icon;
		}).setHeader("Context").setAutoWidth(true);	
		addComponentColumn(address -> {
			StringBuilder formattedAddress = new StringBuilder();
			formattedAddress.append(address.getStreet() != null ? address.getStreet().concat(",") : "");
			formattedAddress.append(address.getZip() != null ? address.getZip().concat(",") : "");
			formattedAddress.append(address.getCity() != null ? address.getCity() : "");
			String formattedAddressStr = formattedAddress.toString();
			if(formattedAddressStr.endsWith(",")) formattedAddressStr = formattedAddressStr.substring(0, formattedAddressStr.length()-1);
			return new Label(formattedAddressStr);
		}).setHeader("Address").setAutoWidth(true);	
		setVisible(false);
	}
	
	@Override
	public GridListDataView<Address> setItems(Collection<Address> items) {
		if(items == null || items.isEmpty()) {
			addresses.clear();
			setVisible(false);
		}
		else {
			addresses.addAll(items);
			setVisible(true);
		}
		return super.setItems(items);
	}
	
	public List<Address> getItems() {
		return addresses;
	}

}
