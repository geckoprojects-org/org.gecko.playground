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
import org.gecko.playground.mongo.PersonPersistenceService;
import org.gecko.playground.search.PersonIndexMongoService;
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
		"osgi.command.function=reindex", 
		"osgi.command.function=savePerson", 
		"osgi.command.function=removePerson"
})
public class PersonIndexCommands {
	
	@Reference
	PersonIndexService personIndexService;
	
	@Reference
	PersonSearchService personSearchService;
	
	@Reference
	PersonIndexMongoService personIndexMongoService;
	
	@Reference
	PersonPersistenceService personPersistenceService;
	
	@Reference
	PersonPackage personPackage;
	
	/** Saves a new person in MongoDB and indexes it - the same flow as the e4 person form. */
	public void savePerson(String firstName, String lastName) {
		Person person = personPackage.getPersonFactory().createPerson();
		person.setId(UUID.randomUUID().toString());
		person.setFirstNames(firstName);
		person.setLastName(lastName);
		person = personPersistenceService.savePerson(person);
		personIndexService.indexPerson(person, true);
		System.out.println("Person saved and indexed with id: " + person.getId());
	}
	
	/** Rebuilds the person index from all persons stored in MongoDB. */
	public void reindex() throws Exception {
		Long count = personIndexMongoService.initializeIndex().getValue();
		System.out.println(count + " Person(s) indexed");
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
