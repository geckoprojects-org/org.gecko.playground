/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.playground.search.cmd;

import java.util.List;

import org.gecko.playground.model.person.Person;
import org.gecko.playground.search.PersonSearchService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * 
 * @author ilenia
 * @since Mar 9, 2023
 */
@Component(name = "PersonSearchCommands", service = PersonSearchCommands.class, property = {
		"osgi.command.scope=search", //
		"osgi.command.function=searchByFirstName", 
		"osgi.command.function=searchByLastName"
})
public class PersonSearchCommands {
	
	@Reference
	PersonSearchService personSearchService;
	
	public void searchByFirstName(String query, boolean exactMatch) {
		displayResult(personSearchService.searchPersonsByFirstName(query, exactMatch));
		
	}
	
	public void searchByLastName(String query, boolean exactMatch) {
		displayResult(personSearchService.searchPersonsByLastName(query, exactMatch));
		
	}
	
	private void displayResult(List<Person> result) {
		if(result == null || result.isEmpty()) System.out.println("No Person object matching your search criteria has been found.");
		else {
			for(Person p : result) {
				System.out.println("--------Matching Result------------");
				System.out.println("First Name: " + p.getFirstNames());
				System.out.println("Last Name: " + p.getLastName());
			}
		}
	}

}
