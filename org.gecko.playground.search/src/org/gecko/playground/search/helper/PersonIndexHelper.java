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
package org.gecko.playground.search.helper;

import java.util.Collections;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.Term;
import org.gecko.playground.model.person.Person;
import org.gecko.search.api.IndexActionType;
import org.gecko.search.document.DocumentIndexContextObject;
import org.gecko.search.document.DocumentIndexContextObjectImpl;
import org.gecko.search.util.DeferredCommitCallback;
import org.gecko.search.util.DocumentUtil;
import org.osgi.util.promise.Deferred;

/**
 * 
 * @author ilenia
 * @since Mar 9, 2023
 */
public class PersonIndexHelper {
	
	public static final String PERSON_ID = "person_id";
	public static final String PERSON_FIRST_NAME_LOWER = "person_first_name_lower";
	public static final String PERSON_LAST_NAME_LOWER = "person_last_name_lower";
	public static final String PERSON_FIRST_NAME = "person_first_name";
	public static final String PERSON_LAST_NAME = "person_last_name";

	
	public static DocumentIndexContextObject mapPersonNew(Person person) {		
		return mapPerson(person, IndexActionType.ADD, null);
	}

	public static DocumentIndexContextObject mapPersonNew(Person person, Deferred<Boolean> deferred) {
		return mapPerson(person, IndexActionType.ADD, deferred);
	}

	public static DocumentIndexContextObject mapPersonUpdate(Person person, Deferred<Boolean> deferred) {
		return mapPerson(person, IndexActionType.MODIFY, deferred);
	}
	
	public static DocumentIndexContextObject mapPerson(Person person, IndexActionType indexAction,
			Deferred<Boolean> deferred) {
		
		Document doc = new Document();
		DocumentUtil.toDocument(doc, person);

		doc.add(new StringField(PERSON_ID, person.getId(), Store.YES));

		if(person.getFirstNames() != null) {
			doc.add(new StringField(PERSON_FIRST_NAME_LOWER, person.getFirstNames().toLowerCase(), Store.NO));
			doc.add(new StringField(PERSON_FIRST_NAME, person.getFirstNames(), Store.NO));
		}
		if(person.getLastName() != null) {
			doc.add(new StringField(PERSON_LAST_NAME_LOWER, person.getLastName().toLowerCase(), Store.NO));
			doc.add(new StringField(PERSON_LAST_NAME, person.getLastName(), Store.NO));
		}

		DocumentIndexContextObjectImpl.Builder builder = DocumentIndexContextObjectImpl.builder()
				.withDocuments(Collections.singletonList(doc)).withSourceObject(person)
				.withIndexActionType(indexAction);

		if (IndexActionType.MODIFY.equals(indexAction) || IndexActionType.REMOVE.equals(indexAction)) {
			builder.withIdentifyingTerm(new Term("id", person.getId()));
		}
		if (deferred != null) {
			builder = builder.withCommitCallback(new DeferredCommitCallback(deferred));
		}

		return builder.build();
	}

}
