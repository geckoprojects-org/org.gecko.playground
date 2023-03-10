package org.gecko.playground.vaadin.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.gecko.playground.model.person.Contact;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class ContactGrid extends Grid<Contact>{
	
	private static final long serialVersionUID = 1829973306932499466L;
	private List<Contact> contacts = new ArrayList<>();

	public ContactGrid() {
		addComponentColumn(contact -> {
			Icon icon;
			switch(contact.getType()) {
			case EMAIL:
				icon = new Icon(VaadinIcon.MAILBOX);
				break;
			case MOBILE:
				icon = new Icon(VaadinIcon.MOBILE);
				break;
			case PHONE:
				icon = new Icon(VaadinIcon.PHONE);
				break;
			case TWITTER:
				icon = new Icon(VaadinIcon.TWITTER);
				break;
			default: case OTHER: case INSTAGRAM: case LINKEDIN: 
				icon = new Icon(VaadinIcon.CHAT);
				break;			
			}
			return icon;
		}).setHeader("Type").setAutoWidth(true);
		addComponentColumn(contact -> {
			Icon icon;
			switch(contact.getContext()) {
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
		addColumn(Contact::getValue).setHeader("Contact").setAutoWidth(true);
		setVisible(false);
	}
	
	@Override
	public GridListDataView<Contact> setItems(Collection<Contact> items) {
		if(items == null || items.isEmpty()) {
			contacts.clear();
			setVisible(false);
		}
		else {
			contacts.addAll(items);
			setVisible(true);
		}
		return super.setItems(items);
	}
	
	public List<Contact> getItems() {
		return contacts;
	}

}
