/**
 * Copyright (c) 2012 - 2026 Data In Motion and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.playground.search;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.fennec.model.query.Query;
import org.eclipse.fennec.model.query.builder.Expressions;
import org.eclipse.fennec.model.query.builder.QueryBuilder;
import org.eclipse.fennec.model.expression.Expression;
import org.eclipse.fennec.persistence.query.api.Hit;
import org.eclipse.fennec.search.query.IndexSearch;
import org.gecko.playground.model.person.Person;
import org.gecko.playground.model.person.PersonPackage;
import org.gecko.playground.search.helper.PersonSearchMapping;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Searches persons in the Eclipse Fennec Search {@code person} index unit.
 * <p>
 * The queries are written in the backend-neutral Fennec query IR. The hits are the original
 * persons from MongoDB, resolved by the {@code PersonPrimaryStore}.
 */
@Component(name = "PersonSearchService", service = PersonSearchService.class)
public class PersonSearchService {

	@Reference(target = "(search.unit.alias=" + PersonSearchMapping.UNIT_NAME + ")")
	private IndexSearch search;

	@Reference
	private PersonPackage personPackage;

	public Person searchPersonById(String personId) {
		Objects.requireNonNull(personId, "Cannot search Person by null id!");
		List<Person> result = execute(Expressions.path(personPackage.getPerson_Id()).eq(personId));
		return result.isEmpty() ? null : result.get(0);
	}

	public List<Person> searchPersonsByFirstName(String firstName, boolean exactMatch) {
		Objects.requireNonNull(firstName, "Cannot search Person by null firstName!");
		return execute(match(personPackage.getPerson_FirstNames(), firstName, exactMatch));
	}

	public List<Person> searchPersonsByLastName(String lastName, boolean exactMatch) {
		Objects.requireNonNull(lastName, "Cannot search Person by null lastName!");
		return execute(match(personPackage.getPerson_LastName(), lastName, exactMatch));
	}

	public List<Person> searchPersonsName(String name) {
		Objects.requireNonNull(name, "Cannot search Person by null name!");
		if (name.isBlank()) {
			return execute(null);
		}
		return execute(Expressions.or(
				match(personPackage.getPerson_FirstNames(), name, false),
				match(personPackage.getPerson_LastName(), name, false)));
	}

	/**
	 * Exact: the whole value. Otherwise: a case insensitive substring or a value within one
	 * edit (typo tolerance). An empty value searches for all persons.
	 */
	private Expression match(EAttribute attribute, String value, boolean exactMatch) {
		if (value.isBlank()) {
			return null;
		}
		if (exactMatch) {
			return Expressions.path(attribute).eq(value);
		}
		return Expressions.or(
				Expressions.path(attribute).containsIgnoreCase(value),
				Expressions.path(attribute).fuzzy(value, 1));
	}

	private List<Person> execute(Expression predicate) {
		QueryBuilder builder = QueryBuilder.from(personPackage.getPerson());
		if (predicate != null) {
			builder.where(predicate);
		}
		Query query = builder.build();
		try {
			return search.search(query).stream()
					.map(Hit::object)
					.filter(Person.class::isInstance)
					.map(Person.class::cast)
					.toList();
		} catch (Exception e) {
			System.err.println("Exception while searching for Persons with query " + query);
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
}
