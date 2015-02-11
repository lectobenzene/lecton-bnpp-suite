package com.mobility.bnpp.urlencryption.plugincode;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.mobility.bnpp.urlencryption.corecode.EncoderDecoder;
import com.mobility.bnpp.urlencryption.corecode.MR1Utils;

public class InputDialog extends Dialog {

	private Text txtClearText;
	private Text txtEncryptedText;

	protected InputDialog(Shell parentShell) {
		super(parentShell);
	}

	private String clearText;
	private String encryptedText;
	private Label lblStatus;

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FormLayout());
		
		Label lblClearText = new Label(container, SWT.NONE);
		FormData fd_lblClearText = new FormData();
		fd_lblClearText.left = new FormAttachment(0, 10);
		fd_lblClearText.top = new FormAttachment(0, 10);
		lblClearText.setLayoutData(fd_lblClearText);
		lblClearText.setText("Clear Text");
		
		lblStatus = new Label(container, SWT.HORIZONTAL | SWT.CENTER);
		lblStatus.setAlignment(SWT.CENTER);
		FormData fd_lblStatus = new FormData();
		fd_lblStatus.right = new FormAttachment(100, -10);
		fd_lblStatus.left = new FormAttachment(0, 10);
		fd_lblStatus.bottom = new FormAttachment(100, -10);
		lblStatus.setLayoutData(fd_lblStatus);
		lblStatus.setText("Status");
		
		txtClearText = new Text(container, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		fd_lblClearText.right = new FormAttachment(txtClearText, -6);
		FormData fd_txtClearText = new FormData();
		fd_txtClearText.right = new FormAttachment(100, -10);
		fd_txtClearText.left = new FormAttachment(0, 96);
		fd_txtClearText.top = new FormAttachment(0, 10);
		fd_txtClearText.bottom = new FormAttachment(0, 76);
		txtClearText.setLayoutData(fd_txtClearText);
		
		Label lblEncyptedText = new Label(container, SWT.NONE);
		FormData fd_lblEncyptedText = new FormData();
		fd_lblEncyptedText.bottom = new FormAttachment(lblStatus, -76);
		fd_lblEncyptedText.top = new FormAttachment(lblClearText, 77);
		fd_lblEncyptedText.left = new FormAttachment(0, 10);
		lblEncyptedText.setLayoutData(fd_lblEncyptedText);
		lblEncyptedText.setText("Encypted Text");
		
		txtEncryptedText = new Text(container, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		fd_lblStatus.top = new FormAttachment(txtEncryptedText, 6);
		FormData fd_txtEncryptedText = new FormData();
		fd_txtEncryptedText.right = new FormAttachment(100, -10);
		fd_txtEncryptedText.left = new FormAttachment(lblEncyptedText, 6);
		fd_txtEncryptedText.top = new FormAttachment(txtClearText, 25);
		fd_txtEncryptedText.bottom = new FormAttachment(100, -43);
		txtEncryptedText.setLayoutData(fd_txtEncryptedText);
		
		return container;
	}

	@Override
	protected void cancelPressed() {
		txtClearText.setText("");
		txtEncryptedText.setText("");
	}
	
	
	@Override
	   protected void createButtonsForButtonBar(Composite parent) {
	    super.createButtonsForButtonBar(parent);

	    Button ok = getButton(IDialogConstants.OK_ID);
	    ok.setText("Proceed");
	    ok.setSize(600, 50);
	    setButtonLayoutData(ok);
	    
	    ((GridLayout) parent.getLayout()).numColumns += 2;
	    
	    Button cancel = getButton(IDialogConstants.CANCEL_ID);
	    cancel.setText("Clear");
	    setButtonLayoutData(cancel);
	    
	    
	 }

	@Override
	protected void okPressed() {
		clearText = txtClearText.getText();
		encryptedText = txtEncryptedText.getText();
		
		if(isStringNotEmpty(clearText) && isStringNotEmpty(encryptedText)){
			String temporaryClearText =  EncoderDecoder.decrypt(MR1Utils.getEncSeed(), encryptedText);
			if(temporaryClearText.equals(clearText)){
				lblStatus.setText("Encryption is Correct");
			}else{
				lblStatus.setText("Invalid Encryption");
			}
			
		}else if(isStringNotEmpty(clearText)){
			encryptedText = EncoderDecoder.encrypt(MR1Utils.getEncSeed(), clearText);
			txtEncryptedText.setText(encryptedText);
		}else if(isStringNotEmpty(encryptedText)){
			String clearText = EncoderDecoder.decrypt(MR1Utils.getEncSeed(), encryptedText);
			txtClearText.setText(clearText);
		}else{
			lblStatus.setText("Something is Wrong");
		}
	}

	
	@Override
	protected Control createButtonBar(Composite parent) {

		return super.createButtonBar(parent);
	}

	
	
	private boolean isStringNotEmpty(String string) {
		if (string != null && !"".equalsIgnoreCase(string)) {
			return true;
		} else {
			return false;
		}
	}
}
