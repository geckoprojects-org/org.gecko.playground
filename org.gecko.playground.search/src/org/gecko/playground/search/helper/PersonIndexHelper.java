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
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.Term;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gecko.emf.search.document.EObjectContextObjectBuilder;
import org.gecko.emf.search.document.EObjectDocumentIndexObjectContext;
import org.gecko.playground.model.person.Person;
import org.gecko.search.IndexActionType;
import org.gecko.search.util.DocumentUtil;

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

	
	public static EObjectDocumentIndexObjectContext mapPersonNew(Person person) {		
		return mapPerson(person, IndexActionType.ADD);
	}

	public static EObjectDocumentIndexObjectContext mapPersonUpdate(Person person) {
		return mapPerson(person, IndexActionType.MODIFY);
	}
	
	public static EObjectDocumentIndexObjectContext mapPerson(Person person, IndexActionType indexAction) {
		
		Document doc = new Document();
		EcoreUtil.resolveAll(person);
		DocumentUtil.toDocument(doc, person, Map.of(DocumentUtil.INDEX_NON_CONTAINEMENT, true));
		

		doc.add(new StringField(PERSON_ID, person.getId(), Store.YES));

		if(person.getFirstNames() != null) {
			doc.add(new StringField(PERSON_FIRST_NAME_LOWER, person.getFirstNames().toLowerCase(), Store.NO));
			doc.add(new StringField(PERSON_FIRST_NAME, person.getFirstNames(), Store.NO));
		}
		if(person.getLastName() != null) {
			doc.add(new StringField(PERSON_LAST_NAME_LOWER, person.getLastName().toLowerCase(), Store.NO));
			doc.add(new StringField(PERSON_LAST_NAME, person.getLastName(), Store.NO));
		}

		EObjectContextObjectBuilder builder = (EObjectContextObjectBuilder) EObjectContextObjectBuilder.create()
				.withDocuments(Collections.singletonList(doc)).withSourceObject(person)
				.withIndexActionType(indexAction);

		if (IndexActionType.MODIFY.equals(indexAction) || IndexActionType.REMOVE.equals(indexAction)) {
			builder.withIdentifyingTerm(new Term("id", person.getId()));
		}
		return builder.build();
	}

}
