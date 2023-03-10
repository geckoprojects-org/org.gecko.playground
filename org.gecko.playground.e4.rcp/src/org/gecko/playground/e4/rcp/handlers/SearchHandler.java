package org.gecko.playground.e4.rcp.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class SearchHandler {

	@CanExecute
	public boolean canExecute(EPartService partService) {
		return true;
	}

	@Execute
	public void execute(EPartService ePartService) {
		if(ePartService != null) {
			MPart activePart = ePartService.getActivePart();
			if(activePart != null) activePart.setVisible(false);
			MPart formPart = ePartService.findPart("org.gecko.playground.e4.rcp.part.search");
			if(formPart != null) {
				ePartService.activate(formPart);
				formPart.setVisible(true);
			}
			else {
				System.err.println("Form Part not found!");
			}
		}
		else {
			System.err.println("EPartService not injected!");
		}		
	}
}