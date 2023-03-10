package org.gecko.playground.e4.rcp.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

public class QuitHandler {
	@Execute
	public void execute(IWorkbench workbench, Shell shell) {

		if (MessageDialog.open(MessageDialog.QUESTION_WITH_CANCEL, shell, "Confirmation", "Do you really want to exit?", SWT.NONE)) {
			workbench.close();
		}
	}
}
