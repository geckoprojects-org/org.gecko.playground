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
 * @author mark
 * @since 13.01.2023
 */
@Component
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
