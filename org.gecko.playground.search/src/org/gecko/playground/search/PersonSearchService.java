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
package org.gecko.playground.search;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.playground.model.person.Person;
import org.gecko.playground.search.helper.PersonIndexHelper;
import org.gecko.search.document.LuceneIndexService;
import org.gecko.search.util.DocumentUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * 
 * @author ilenia
 * @since Mar 9, 2023
 */
@Component(name = "PersonSearchService", service = PersonSearchService.class)
public class PersonSearchService {
	
	@Reference(target = "(id=person)")
	private LuceneIndexService personIndex;
	
	@Reference
	private ResourceSet resourceSet;
	
	public Person searchPersonById(String personId) {
		Objects.requireNonNull(personId, "Cannot search Person by null id!");
		Query query = new TermQuery(new Term(PersonIndexHelper.PERSON_ID, personId));
		List<Person> result = executeTermSearch(query);
		if(result != null && !result.isEmpty()) return result.get(0);
		return null;
	}


	public List<Person> searchPersonsByFirstName(String firstName, boolean exactMatch) {
		Objects.requireNonNull(firstName, "Cannot search Person by null firstName!");
		Query query;
		if(exactMatch) {
			query = new TermQuery(new Term(PersonIndexHelper.PERSON_FIRST_NAME, firstName));
			
		} else {
			Query q1 = new WildcardQuery(new Term(PersonIndexHelper.PERSON_FIRST_NAME_LOWER, "*" + firstName.toLowerCase() + "*"));
			Query q2 = new FuzzyQuery(new Term(PersonIndexHelper.PERSON_FIRST_NAME_LOWER, firstName.toLowerCase()));
			query = new BooleanQuery.Builder().add(q1, Occur.SHOULD).add(q2, Occur.SHOULD).build();
		}
		return executeTermSearch(query);
	}


	public List<Person> searchPersonsByLastName(String lastName, boolean exactMatch) {
		Objects.requireNonNull(lastName, "Cannot search Person by null lastName!");
		Query query;
		if(exactMatch) {
			query = new TermQuery(new Term(PersonIndexHelper.PERSON_LAST_NAME, lastName));
			
		} else {
			Query q1 = new WildcardQuery(new Term(PersonIndexHelper.PERSON_LAST_NAME_LOWER, "*" + lastName.toLowerCase() + "*"));
			Query q2 = new FuzzyQuery(new Term(PersonIndexHelper.PERSON_LAST_NAME_LOWER, lastName.toLowerCase()));
			query = new BooleanQuery.Builder().add(q1, Occur.SHOULD).add(q2, Occur.SHOULD).build();
		}
		return executeTermSearch(query);
	}
	
	private List<Person> executeTermSearch(Query query) {

		IndexSearcher searcher = personIndex.aquireSearch();		
		try {
			TopDocs topDocs = searcher.search(query, Integer.MAX_VALUE);
			if (topDocs.scoreDocs.length == 0) {
				return Collections.emptyList();
			}
			IndexReader indexReader = searcher.getIndexReader();
			return Arrays.asList(topDocs.scoreDocs).stream().map(sd -> sd.doc).map(id -> {
				Document d;
				try {
					d = indexReader.storedFields().document(id);
					return d;
				} catch (IOException e) {
					return null;
				}
			}).filter(d -> d != null).map(d -> {
				return (Person) DocumentUtil.toEObject(d, resourceSet);
			}).collect(Collectors.toList());
		} catch (Exception e) {
			System.err.println("Exception while search for Person with query " + query);
			e.printStackTrace();
			return Collections.emptyList();
		} finally {
			personIndex.releaseSearcher(searcher);
		}
	}
}

