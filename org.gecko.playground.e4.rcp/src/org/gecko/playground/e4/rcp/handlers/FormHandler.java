package org.gecko.playground.e4.rcp.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
public class FormHandler {

	@CanExecute
	public boolean canExecute(EPartService ePartService) {
		if(ePartService == null) return false;
		return true;
	}


	@Execute
	public void execute(EPartService ePartService){
		MPart formPart = ePartService.findPart("org.gecko.playground.e4.rcp.part.form");
		if(formPart != null) {
			formPart.setVisible(true);
			ePartService.activate(formPart);				
		}
		else {
			System.err.println("Form Part not found!");
		}
	}

}
