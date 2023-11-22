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
package org.gecko.playground.jpa.classloader;

import org.eclipse.persistence.dynamic.DynamicClassLoader;
import org.eclipse.persistence.internal.helper.ConversionManager;
import org.eclipse.persistence.jpa.dynamic.JPADynamicHelper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/**
 * 
 * @author mark
 * @since 13.01.2023
 */
public class OSGiJPADynamicHelper extends JPADynamicHelper {

	/**
	 * Creates a new instance.
	 * @param emf
	 */
	public OSGiJPADynamicHelper(EntityManagerFactory emf) {
		super(emf);
	}
	
	/**
	 * Creates a new instance.
	 * @param em
	 */
	public OSGiJPADynamicHelper(EntityManager em) {
		super(em);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.persistence.dynamic.DynamicHelper#getDynamicClassLoader()
	 */
	@Override
	public DynamicClassLoader getDynamicClassLoader() {
		ConversionManager cm = null;

        if (session == null) {
            cm = ConversionManager.getDefaultManager();
        } else {
            cm = session.getPlatform().getConversionManager();
        }

        if (cm.getLoader() instanceof DynamicClassLoader) {
            return (DynamicClassLoader) cm.getLoader();
        }

        DynamicClassLoader dcl = new DynamicClassLoader(getClass().getClassLoader());
        cm.setLoader(dcl);

        if (session == null) {
            ConversionManager.setDefaultLoader(dcl);
        }

        return dcl;
	}

}
