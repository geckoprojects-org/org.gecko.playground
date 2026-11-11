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

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fennec.persistence.repository.api.Repository;
import org.gecko.playground.model.person.Person;
import org.gecko.playground.model.person.PersonPackage;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * Persists {@link Person} objects in MongoDB through the Eclipse Fennec persistence
 * repository configured as {@code playground} (see {@code configs/mongodb.json}).
 * <p>
 * The repository is registered with prototype scope and is not thread-safe, so every
 * instance of this (prototype) service gets its own repository instance.
 */
@Component(name = "PersonPersistenceService", service = PersonPersistenceService.class, scope = ServiceScope.PROTOTYPE)
public class PersonPersistenceService {

	@Reference(target = "(persistence.repository.id=playground)")
	Repository repository;

	@Reference
	PersonPackage personPackage;

	/**
	 * Saves the person together with its (non-containment) addresses and tags.
	 * Afterwards the person is attached to its Mongo collection resource, so it carries its
	 * primary URI (e.g. {@code mongodb://playground/Person#<id>}), which the search index
	 * stores as pointer to the original.
	 * @param person the person to save
	 * @return the saved person
	 */
	public Person savePerson(Person person) {
		Objects.requireNonNull(person, "Cannot save a null Person!");
		List<EObject> objects = new ArrayList<>();
		objects.addAll(person.getAddress());
		objects.addAll(person.getTag());
		objects.add(person);
		try {
			repository.saveAll(objects);
		} catch (IOException e) {
			throw new UncheckedIOException("Could not save Person " + person.getId(), e);
		}
		if (person.eResource() == null) {
			repository.attach(person);
		}
		return person;
	}

	public Person getPersonById(String personId) {
		Objects.requireNonNull(personId, "Cannot retrieve a Person with a null id!");
		try {
			return (Person) repository.getEObject(personPackage.getPerson(), personId);
		} catch (IOException e) {
			throw new UncheckedIOException("Could not load Person " + personId, e);
		}
	}
}
