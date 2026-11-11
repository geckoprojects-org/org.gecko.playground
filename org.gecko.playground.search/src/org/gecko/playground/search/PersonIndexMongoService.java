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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fennec.persistence.repository.api.ReadRepository;
import org.gecko.playground.model.person.Person;
import org.gecko.playground.model.person.PersonPackage;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.util.promise.Promise;
import org.osgi.util.promise.PromiseFactory;

/**
 * Rebuilds the {@code person} search index from all persons stored in MongoDB, once on
 * activation.
 * <p>
 * The Fennec Mongo repository is only registered while the database is reachable (connection
 * liveness), so this component only activates with a working MongoDB.
 */
@Component(immediate = true, name = "PersonIndexMongoService", service = PersonIndexMongoService.class, scope = ServiceScope.SINGLETON)
public class PersonIndexMongoService {

	@Reference(target = "(persistence.repository.id=playground)")
	private ComponentServiceObjects<ReadRepository> repositoryServiceObjects;

	@Reference
	private PersonIndexService personIndexService;

	@Reference
	private PersonPackage personPackage;

	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	private final PromiseFactory factory = new PromiseFactory(executor);

	@Activate
	public void activate() {
		initializeIndex()
			.onFailure(Throwable::printStackTrace);
	}

	@Deactivate
	public void deactivate() {
		executor.shutdownNow();
	}

	public Promise<Long> initializeIndex() {
		return factory.submit(this::indexAll)
				.onSuccess(count -> System.out.println("Finished indexing " + count + " Person object(s)!"));
	}

	private long indexAll() throws Exception {
		System.out.println("Starting indexing Person objects...");
		// A repository instance is not thread-safe: this thread takes its own one
		ReadRepository repository = repositoryServiceObjects.getService();
		AtomicLong count = new AtomicLong();
		try (Stream<EObject> persons = repository.getAllEObjects(personPackage.getPerson())) {
			personIndexService.resetIndex();
			persons.map(Person.class::cast).forEach(person -> {
				personIndexService.addToIndex(person);
				count.incrementAndGet();
			});
			personIndexService.commit();
		} finally {
			try {
				repositoryServiceObjects.ungetService(repository);
			} catch (IllegalStateException e) {
				// This component was deactivated meanwhile (e.g. the index unit was rebound at
				// startup), SCR has already released the repository instance
			}
		}
		return count.get();
	}
}
