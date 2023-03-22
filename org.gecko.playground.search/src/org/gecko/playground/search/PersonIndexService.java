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
package org.gecko.playground.search;

import java.io.IOException;

import org.gecko.playground.model.person.Person;
import org.gecko.playground.search.helper.PersonIndexHelper;
import org.gecko.search.IndexActionType;
import org.gecko.search.document.LuceneIndexService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.gecko.emf.search.document.EObjectDocumentIndexObjectContext;

@Component(immediate = true, name = "PersonIndexService", service = PersonIndexService.class, scope = ServiceScope.SINGLETON)
public class PersonIndexService {

	
	@Reference(target = "(id=person)")
	private LuceneIndexService<EObjectDocumentIndexObjectContext> personIndex;
	
	public void indexPerson(Person person, boolean isFirstSave) {
		if(isFirstSave) {
			indexPerson(person, IndexActionType.ADD);
		}
		else {
			indexPerson(person, IndexActionType.MODIFY);
		}	
	}


	public void deletePerson(Person person) {
		indexPerson(person, IndexActionType.REMOVE);		
	}


	public void resetIndex() {
		try {
			personIndex.getIndexWriter().deleteAll();
			personIndex.commit();
		} catch (IOException e) {
			System.err.println("Could not delete Person index " + e);
		}		

	}

	private void indexPerson(Person person, IndexActionType actionType) {
		EObjectDocumentIndexObjectContext context = PersonIndexHelper.mapPerson(person, actionType);			
		personIndex.handleContextSync(context);
	}
}