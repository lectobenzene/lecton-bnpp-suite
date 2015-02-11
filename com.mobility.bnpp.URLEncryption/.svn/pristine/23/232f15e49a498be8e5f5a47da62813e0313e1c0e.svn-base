package com.mobility.bnpp.urlencryption.plugincode;

import java.security.Security;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.mobility.bnpp.urlencryption.corecode.EncoderDecoder;
import com.mobility.bnpp.urlencryption.corecode.MR1Utils;

public class URLEncryption extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		Security.addProvider(new org.apache.harmony.security.provider.crypto.CryptoProvider());
		
		IWorkbenchWindow window =  HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		InputDialog myDialog = new InputDialog(window.getShell());
		myDialog.open();
		
		return null;
	}

}
