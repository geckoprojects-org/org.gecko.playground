/**
 * Copyright (c) 2012 - 2022 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.playground.ds.greedy;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

@Component
public class GreedyConsumer {
	
	@Activate
	public void activate() {
		System.out.println("Activate Greedy Consumer");
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("De-activate Greedy Consumer");
	}
	
//	@Reference
	@Reference(policy = ReferencePolicy.DYNAMIC, cardinality = ReferenceCardinality.OPTIONAL, policyOption = ReferencePolicyOption.GREEDY)
//	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	public void setImportant(Important i) {
		String importance = i.very() ? "VERY" : "less";
		System.out.println("Set " + importance + " important service: " + i.important() + ", very: " + i.very() + " from " + i);
	}
	
	public void modifiedImportant(Important i) {
		String importance = i.very() ? "VERY" : "less";
		System.out.println("Modified " + importance + " important service: " + i.important() + ", very: " + i.very() + " from " + i);
	}
	
	public void unsetImportant(Important i) {
		String importance = i.very() ? "VERY" : "less";
		System.out.println("Unset " + importance + " important service: " + i.important() + ", very: " + i.very() + " from " + i);
	}

}
