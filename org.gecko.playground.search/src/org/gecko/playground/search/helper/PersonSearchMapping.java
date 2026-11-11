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
package org.gecko.playground.search.helper;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.fennec.search.esearch.DocumentMapping;
import org.eclipse.fennec.search.esearch.ESearchFactory;
import org.eclipse.fennec.search.esearch.IndexUnitMapping;
import org.eclipse.fennec.search.esearch.KeywordFieldMapping;
import org.eclipse.fennec.search.esearch.Materialization;
import org.eclipse.fennec.search.esearch.MaterializationKind;
import org.gecko.playground.model.person.PersonPackage;

/**
 * The Eclipse Fennec Search mapping of the {@code person} index unit.
 * <p>
 * Everything not declared here is mapped by convention (the id as stored keyword, strings as
 * analyzed text). Two declarations on top:
 * <ul>
 * <li>{@code firstNames} and {@code lastName} additionally as keyword fields, so exact, case
 * insensitive and fuzzy matches run on whole values,</li>
 * <li>{@code SOURCE_URI} materialization: the index is a secondary index beside MongoDB and only
 * stores where the original person lives. Hits are resolved through the
 * {@link PersonPrimaryStore}.</li>
 * </ul>
 */
public final class PersonSearchMapping {

	/** Name of the index unit and of its mapping (= {@code search.unit.alias}). */
	public static final String UNIT_NAME = "person";

	private PersonSearchMapping() {
	}

	public static IndexUnitMapping create(PersonPackage personPackage) {
		ESearchFactory factory = ESearchFactory.eINSTANCE;
		IndexUnitMapping mapping = factory.createIndexUnitMapping();
		mapping.setName(UNIT_NAME);
		mapping.setEPackage(personPackage);

		DocumentMapping person = factory.createDocumentMapping();
		person.setEClass(personPackage.getPerson());
		person.getFields().add(keyword(personPackage.getPerson_FirstNames()));
		person.getFields().add(keyword(personPackage.getPerson_LastName()));

		Materialization sourceUri = factory.createMaterialization();
		sourceUri.setKind(MaterializationKind.SOURCE_URI);
		person.setMaterialization(sourceUri);

		mapping.getDocuments().add(person);
		return mapping;
	}

	private static KeywordFieldMapping keyword(EAttribute attribute) {
		KeywordFieldMapping keyword = ESearchFactory.eINSTANCE.createKeywordFieldMapping();
		keyword.setFeature(attribute);
		return keyword;
	}
}
