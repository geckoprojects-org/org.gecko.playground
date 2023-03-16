/**
 * Copyright (c) 2012 - 2018 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.playground.mongo;

import java.util.Collection;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.gecko.emf.repository.EMFRepository;
import org.gecko.playground.model.person.Person;
import org.gecko.playground.model.person.PersonPackage;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;
import org.osgi.service.component.annotations.ServiceScope;

@Component(name = "PersonPersistenceService", service = PersonPersistenceService.class, scope = ServiceScope.PROTOTYPE)
public class PersonPersistenceService {

	@Reference(target="(repo_id=playground.playground)", scope = ReferenceScope.PROTOTYPE_REQUIRED)
	EMFRepository repository;
	
	@Reference
	PersonPackage personPackage;

	public Person savePerson(Person person) {
		save(person.getAddress());
		save(person.getTag());
		repository.save(person);
		repository.detach(person);
		return person;
	}
	
	public Person getPersonById(String personId) {
		Objects.requireNonNull(personId, "Cannot retrieve a Person with a null id!");
		return repository.getEObject(personPackage.getPerson(), personId);
	}
	
	
	@SuppressWarnings("unchecked")
	private <T extends EObject> void save(Collection<T> objects) {
		repository.save((Collection<EObject>)objects);
	}
}
