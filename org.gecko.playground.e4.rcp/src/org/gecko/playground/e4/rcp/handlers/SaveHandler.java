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

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.gecko.playground.e4.rcp.parts.PersonFormPart;

/**
 * 
 * @author ilenia
 * @since Mar 14, 2023
 */
public class SaveHandler {
	
	@CanExecute
	public boolean canExecute(EPartService ePartService) {
		MPart formPart = ePartService.findPart("org.gecko.playground.e4.rcp.part.form");
		if(formPart == null || !formPart.isVisible() || !formPart.isDirty()) {
			return false;
		}
		return true;
	}
	
	@Execute
	public void execute(EPartService ePartService) {
		MPart formPart = ePartService.findPart("org.gecko.playground.e4.rcp.part.form");
		Object partObj = formPart.getObject();
		if(partObj instanceof PersonFormPart) {
			((PersonFormPart) partObj).save();
		}
	}

}
