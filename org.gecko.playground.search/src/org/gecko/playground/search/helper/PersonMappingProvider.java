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

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.eclipse.fennec.emf.osgi.eobject.registry.EObjectProvider;
import org.eclipse.fennec.emf.osgi.eobject.registry.EObjectRegistryWriter;
import org.gecko.playground.model.person.PersonPackage;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Delivers the {@link PersonSearchMapping} into the {@code search-mappings} EObject registry,
 * from which the Fennec Search components resolve the mapping of the {@code person} unit.
 * The registry itself is configured in {@code configs/index.json}.
 */
@Component(name = "PersonMappingProvider", service = EObjectProvider.class,
		property = "emf.eobject.provider.name=" + PersonMappingProvider.PROVIDER_NAME)
public class PersonMappingProvider implements EObjectProvider {

	public static final String PROVIDER_NAME = "playground-person-mapping";

	@Reference
	private PersonPackage personPackage;

	@Override
	public CompletableFuture<Void> load(EObjectRegistryWriter writer) {
		writer.put(PROVIDER_NAME, PersonSearchMapping.UNIT_NAME, PersonSearchMapping.create(personPackage), Map.of());
		return CompletableFuture.completedFuture(null);
	}
}
