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

import java.io.IOException;
import java.io.UncheckedIOException;

import org.eclipse.fennec.search.mapping.DocumentMapper;
import org.eclipse.fennec.search.mapping.IndexSchema;
import org.eclipse.fennec.search.mapping.MappedDocument;
import org.eclipse.fennec.search.unit.IndexUnit;
import org.gecko.playground.model.person.Person;
import org.gecko.playground.model.person.PersonPackage;
import org.gecko.playground.search.helper.PersonSearchMapping;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * Writes persons into the Eclipse Fennec Search {@code person} index unit.
 * <p>
 * The index is a secondary index: a person must already live in its MongoDB resource (i.e. be
 * saved by the persistence service) before it is indexed, because the index stores the
 * person's primary URI only.
 */
@Component(immediate = true, name = "PersonIndexService", service = PersonIndexService.class, scope = ServiceScope.SINGLETON)
public class PersonIndexService {

	private final IndexUnit personIndex;
	private final DocumentMapper mapper;

	@Activate
	public PersonIndexService(
			@Reference(target = "(search.unit.alias=" + PersonSearchMapping.UNIT_NAME + ")") IndexUnit personIndex,
			@Reference PersonPackage personPackage) {
		this.personIndex = personIndex;
		this.mapper = DocumentMapper.of(IndexSchema.of(PersonSearchMapping.create(personPackage)));
	}

	/**
	 * Adds the person to the index or replaces its document. Both cases are the same
	 * update-by-id for Lucene, the flag is kept for API compatibility.
	 */
	public void indexPerson(Person person, boolean isFirstSave) {
		addToIndex(person);
		try {
			commit();
		} catch (IOException e) {
			throw new UncheckedIOException("Could not commit the index after Person " + person.getId(), e);
		}
	}

	/**
	 * Adds or replaces the person's document without committing, for bulk indexing.
	 * Call {@link #commit()} afterwards.
	 */
	public void addToIndex(Person person) {
		MappedDocument document = mapper.map(person);
		try {
			personIndex.updateDocuments(document.term(), document.documents());
		} catch (IOException e) {
			throw new UncheckedIOException("Could not index Person " + person.getId(), e);
		}
	}

	public void deletePerson(Person person) {
		try {
			personIndex.deleteDocuments(mapper.map(person).term());
			commit();
		} catch (IOException e) {
			throw new UncheckedIOException("Could not remove Person " + person.getId() + " from the index", e);
		}
	}

	public void resetIndex() {
		try {
			personIndex.deleteAll();
			commit();
		} catch (IOException e) {
			System.err.println("Could not delete Person index " + e);
		}
	}

	/** Commits and refreshes, so the next search sees the changes (read-your-writes). */
	public void commit() throws IOException {
		personIndex.commit();
		personIndex.refresh();
	}
}
