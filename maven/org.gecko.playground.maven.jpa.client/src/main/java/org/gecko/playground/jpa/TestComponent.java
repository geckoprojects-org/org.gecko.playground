/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.playground.jpa;

import org.osgi.annotation.bundle.Capability;
import org.osgi.annotation.bundle.Requirement;
import org.osgi.annotation.bundle.Requirements;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;

import jakarta.persistence.EntityManagerFactory;

/**
 * https://wiki.eclipse.org/EclipseLink/Examples/JPA/Dynamic
 * https://wiki.eclipse.org/EclipseLink/Examples/JPA/Dynamic/CustomizeAttributes
 * https://wiki.eclipse.org/EclipseLink/Examples/Foundation/DynamicPersistence
 * 
 * We provide an own capability by providing a own namespace/category 'playground.jpa' and a name 'accounts'.
 * You can have multiple capabilities with the same namespace, but other names.
 * 
 * On the other hand we defines requirements, that we need certain bundles to run the example.
 * 
 * In the launch.bndrun the capability 'playground.jpa=accounts' will be used to resolve the system.
 * This annotations (@Capability and @Requirement) causing corresponding instructions in the bundles MANIFEST.MF 
 * 
 * @author mark
 * @since 13.01.2023
 */
@Component
@Capability(namespace = "playground.jpa", name = "accounts")
@Requirements({
	@Requirement(namespace = "osgi.identity", filter = "(osgi.identity=org.gecko.persistence.derby)"),
	@Requirement(namespace = "osgi.identity", filter = "(osgi.identity=derby)"),
	@Requirement(namespace = "osgi.identity", filter = "(osgi.identity=org.eclipse.gemini.jpa)"),
	@Requirement(namespace = "osgi.identity", filter = "(osgi.identity=org.eclipse.persistence.jpa.jpql)")
})
public class TestComponent {
	
	@Reference(target = "(" + EntityManagerFactoryBuilder.JPA_UNIT_NAME + "=Accounts)")
    private EntityManagerFactory emf;
	
	
	@Activate
	public void activate(BundleContext ctx) {
		System.out.println("Got basic model");
        System.out.println("Gemini JPA Account Sample started");
        new AccountClient().run(emf);
	}

}
