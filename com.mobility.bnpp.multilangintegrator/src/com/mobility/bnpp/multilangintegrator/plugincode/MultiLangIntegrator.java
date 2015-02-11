package com.mobility.bnpp.multilangintegrator.plugincode;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.mobility.bnpp.multilangintegrator.corecode.MultiLangParser;

public class MultiLangIntegrator extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		System.out.println("Executing...");
		EntryDialog dialog = new EntryDialog(shell);
		dialog.open();
		
		MultiLangParser parser = new MultiLangParser();
		parser.runParser();
		return null;
	}

}
