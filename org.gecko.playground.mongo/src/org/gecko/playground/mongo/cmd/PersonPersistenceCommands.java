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
package org.gecko.playground.mongo.cmd;

import org.gecko.playground.model.person.Person;
import org.gecko.playground.model.person.PersonPackage;
import org.gecko.playground.mongo.PersonPersistenceService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * 
 * @author ilenia
 * @since Mar 15, 2023
 */
@Component(name = "PersonPersistenceCommands", service = PersonPersistenceCommands.class, property = {
		"osgi.command.scope=persistence", //
		"osgi.command.function=savePerson", 
		"osgi.command.function=getPerson"
})
public class PersonPersistenceCommands {
	
	@Reference
	PersonPackage personPackage;
	
	@Reference
	PersonPersistenceService personService;
	
	public void savePerson(String firstName, String lastName) {
		Person person = personPackage.getPersonFactory().createPerson();
		person.setFirstNames(firstName);
		person.setLastName(lastName);
		person = personService.savePerson(person);
		System.out.println(String.format("Saved Person in db with id %s", person.getId()));
	}
	
	public void getPerson(String personId) {
		Person person = personService.getPersonById(personId);
		if(person != null) {
			System.out.println(String.format("Retrieved Person with id %s from db: %s %s", person.getId(), person.getFirstNames(), person.getLastName()));
		} else {
			System.err.println(String.format("No Person with id %s found in db!", personId));
		}
	}

}
