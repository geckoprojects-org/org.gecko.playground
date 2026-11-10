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

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fennec.persistence.repository.api.ReadRepository;
import org.eclipse.fennec.search.query.PrimaryStore;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Resolves the {@code SOURCE_URI} search hits ({@code mongodb://playground/Person#<id>}) to the
 * original persons in MongoDB, so search results carry their addresses, contacts and tags.
 * <p>
 * The Fennec Search {@code IndexSearch} service binds this {@link PrimaryStore} automatically.
 */
@Component(name = "PersonPrimaryStore", service = PrimaryStore.class)
public class PersonPrimaryStore implements PrimaryStore {

	/** A repository instance is not thread-safe, so every access is serialized. */
	@Reference(target = "(persistence.repository.id=playground)")
	private ReadRepository repository;

	@Override
	public synchronized Map<URI, EObject> resolve(List<URI> sourceUris) throws IOException {
		Map<URI, EObject> resolved = new LinkedHashMap<>();
		for (URI uri : sourceUris) {
			EObject object = repository.getEObject(uri);
			if (object != null) {
				resolved.put(uri, object);
			}
		}
		return resolved;
	}
}
