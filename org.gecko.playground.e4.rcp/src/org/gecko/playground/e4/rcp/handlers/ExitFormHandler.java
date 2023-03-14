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
package org.gecko.playground.e4.rcp.handlers;

import java.util.Collection;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.ISaveHandler;
import org.gecko.playground.e4.rcp.parts.PersonFormPart;

/**
 * 
 * @author ilenia
 * @since Mar 14, 2023
 */
public class ExitFormHandler implements ISaveHandler {

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.e4.ui.workbench.modeling.ISaveHandler#save(org.eclipse.e4.ui.model.application.ui.basic.MPart, boolean)
	 */
	@Override
	public boolean save(MPart dirtyPart, boolean confirm) {
		Object partObj = dirtyPart.getObject();
		if(partObj instanceof PersonFormPart) {
			 ((PersonFormPart) partObj).exit();
		}
		return true;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.e4.ui.workbench.modeling.ISaveHandler#saveParts(java.util.Collection, boolean)
	 */
	@Override
	public boolean saveParts(Collection<MPart> dirtyParts, boolean confirm) {
		// TODO Auto-generated method stub
		return false;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.e4.ui.workbench.modeling.ISaveHandler#promptToSave(org.eclipse.e4.ui.model.application.ui.basic.MPart)
	 */
	@Override
	public Save promptToSave(MPart dirtyPart) {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.e4.ui.workbench.modeling.ISaveHandler#promptToSave(java.util.Collection)
	 */
	@Override
	public Save[] promptToSave(Collection<MPart> dirtyParts) {
		// TODO Auto-generated method stub
		return null;
	}

}
