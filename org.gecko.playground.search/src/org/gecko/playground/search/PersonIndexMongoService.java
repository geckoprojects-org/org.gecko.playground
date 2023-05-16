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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.ecore.EObject;
import org.gecko.emf.mongo.Options;
import org.gecko.emf.mongo.pushstream.constants.MongoPushStreamConstants;
import org.gecko.emf.osgi.ResourceSetFactory;
import org.gecko.emf.osgi.UriMapProvider;
import org.gecko.emf.pushstream.EPushStreamProvider;
import org.gecko.emf.repository.EMFRepository;
import org.gecko.emf.repository.query.IQuery;
import org.gecko.emf.repository.query.QueryRepository;
import org.gecko.playground.model.person.Person;
import org.gecko.playground.model.person.PersonPackage;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.util.promise.Promise;
import org.osgi.util.promise.PromiseFactory;
import org.osgi.util.pushstream.PushEvent;
import org.osgi.util.pushstream.PushStream;
import org.osgi.util.pushstream.PushbackPolicyOption;
import org.osgi.util.pushstream.QueuePolicyOption;


/**
 * 
 * @author ilenia
 * @since Mar 16, 2023
 */
@Component(immediate = true, name = "PersonIndexMongoService", service = PersonIndexMongoService.class, 
scope = ServiceScope.SINGLETON,
reference = {
		@Reference(name = "mongoCondition", service = UriMapProvider.class, target = "(uri.map.src=mongodb://playground/)", cardinality = ReferenceCardinality.MANDATORY),
		@Reference(name = "modelCondition", service = ResourceSetFactory.class, target = "(emf.model.name=person)", cardinality = ReferenceCardinality.MANDATORY)
})
public class PersonIndexMongoService {

	@Reference(target = "(repo_id=playground.playground)", cardinality = ReferenceCardinality.MANDATORY)
	private ComponentServiceObjects<EMFRepository> repositoryServiceObjects;
	
	@Reference
	private PersonIndexService personIndexService;
	
	@Reference
	PersonPackage personPackage;
	
	private PromiseFactory factory = new PromiseFactory(Executors.newFixedThreadPool(4));
	
	@Activate
	public void activate() {
		indexPersons();
	}
	
	private void indexPersons() {
		factory.submit(() -> {
			CountDownLatch latch = new CountDownLatch(1);
			latch.await(100, TimeUnit.MILLISECONDS);
			initializeIndex();
			return true;
		}).onSuccess(t -> System.out.println("Finished indexing Person objects!"))
		.onFailure(t -> t.printStackTrace());		
	}
	
	public Promise<Void> initializeIndex() {
		System.out.println("Starting indexing Person objects...");
		
		EMFRepository repository = repositoryServiceObjects.getService();
		QueryRepository queryRepo = (QueryRepository) repository.getAdapter(QueryRepository.class);
		IQuery query = queryRepo.createQueryBuilder().allQuery().build();

		personIndexService.resetIndex();
		
		EPushStreamProvider psp = queryRepo.getEObjectByQuery(personPackage.getPerson(), query,getLoadOptions());
		if(psp == null) {
			return null;
		}
		
		PushStream<EObject> indexNew = psp.createPushStreamBuilder()
				.withPushbackPolicy(PushbackPolicyOption.LINEAR, 50)
				.withQueuePolicy(QueuePolicyOption.BLOCK)
				.withExecutor(Executors.newSingleThreadExecutor())
				.withBuffer(new ArrayBlockingQueue<PushEvent<? extends EObject>>(100))
				.build();
		
		Promise<Void> resultPromise = indexNew
				.map(eo -> (Person) eo)
				.forEach(p -> personIndexService.indexPerson(p, true));
		
		return resultPromise.onFailure(t -> repositoryServiceObjects.ungetService(repository))
				.thenAccept(result -> System.out.println("Finished indexing Person object(s)!"))
				.thenAccept(result -> repositoryServiceObjects.ungetService(repository));
	}
	
	public static Map<Object, Object> getLoadOptions(){
		Map<Object, Object> loadOptions = new HashMap<>();
		loadOptions.put(Options.OPTION_BATCH_SIZE, Integer.valueOf(600));
		loadOptions.put(MongoPushStreamConstants.OPTION_QUERY_PUSHSTREAM, Boolean.TRUE);
		loadOptions.put(Options.OPTION_PROXY_ATTRIBUTES, true);
		return loadOptions;
	}
}
