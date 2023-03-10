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

import java.util.UUID;

import org.gecko.playground.model.person.Person;
import org.gecko.playground.model.person.PersonPackage;
import org.gecko.playground.search.PersonIndexService;
import org.gecko.playground.search.PersonSearchService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * 
 * @author ilenia
 * @since Mar 9, 2023
 */
@Component(name = "PersonIndexCommands", service = PersonIndexCommands.class, property = {
		"osgi.command.scope=index", //
		"osgi.command.function=indexPerson", 
		"osgi.command.function=removePerson"
})
public class PersonIndexCommands {
	
	@Reference
	PersonIndexService personIndexService;
	
	@Reference
	PersonSearchService personSearchService;
	
	@Reference
	PersonPackage personPackage;
	
	public void indexPerson(String firstName, String lastName) {
		Person person = personPackage.getPersonFactory().createPerson();
		person.setId(UUID.randomUUID().toString());
		person.setFirstNames(firstName);
		person.setLastName(lastName);
		personIndexService.indexPerson(person, true);
		System.out.println("Person indexed with id: " + person.getId());
	}
	
	public void removePerson(String personId) {
		Person person = personSearchService.searchPersonById(personId);
		if(person == null) System.out.println("No Person with id " + personId + " found.");
		else {
			personIndexService.deletePerson(person);
			System.out.println("Person " + personId + " deleted from index");
		}
	}

}
