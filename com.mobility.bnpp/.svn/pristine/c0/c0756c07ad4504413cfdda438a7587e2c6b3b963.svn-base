package com.mobility.bnpp.integratemultilang;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class InputDialog extends Dialog {
	private Text txtEasyBankingSource;
	private Text txtHelloBankingSource;
	
	private static String easyBankingSource;
	private static String helloBankingSource;
	
	public  String getEasyBankingSource() {
		return easyBankingSource;
	}

	public String getHelloBankingSource() {
		return helloBankingSource;
	}

	
	private Shell shell;
	private boolean isOkPressed = false;
	
	
	public InputDialog(Shell parentShell) {
		super(parentShell);
		shell = parentShell;
	}

	@Override
	protected void buttonPressed(int buttonId) {
		// TODO Auto-generated method stub
		super.buttonPressed(buttonId);
	}

	@Override
	protected void cancelPressed() {
		// TODO Auto-generated method stub
		super.cancelPressed();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FormLayout());
		
		Label lblEasyBanking = new Label(container, SWT.NONE);
		FormData fd_lblEasyBanking = new FormData();
		fd_lblEasyBanking.top = new FormAttachment(0, 39);
		fd_lblEasyBanking.left = new FormAttachment(0, 22);
		fd_lblEasyBanking.bottom = new FormAttachment(0, 53);
		fd_lblEasyBanking.right = new FormAttachment(0, 104);
		lblEasyBanking.setLayoutData(fd_lblEasyBanking);
		lblEasyBanking.setText("Easy Banking");
		
		Label lblHelloBanking = new Label(container, SWT.NONE);
		FormData fd_lblHelloBanking = new FormData();
		fd_lblHelloBanking.right = new FormAttachment(0, 104);
		fd_lblHelloBanking.top = new FormAttachment(lblEasyBanking, 25);
		fd_lblHelloBanking.left = new FormAttachment(0, 22);
		lblHelloBanking.setLayoutData(fd_lblHelloBanking);
		lblHelloBanking.setText("Hello Banking");
		
		txtEasyBankingSource = new Text(container, SWT.BORDER);
		FormData fd_txtEasyBankingSource = new FormData();
		fd_txtEasyBankingSource.right = new FormAttachment(lblEasyBanking, 246, SWT.RIGHT);
		fd_txtEasyBankingSource.top = new FormAttachment(0, 39);
		fd_txtEasyBankingSource.left = new FormAttachment(lblEasyBanking, 6);
		txtEasyBankingSource.setLayoutData(fd_txtEasyBankingSource);
		
		txtHelloBankingSource = new Text(container, SWT.BORDER);
		FormData fd_txtHelloBankingSource = new FormData();
		fd_txtHelloBankingSource.right = new FormAttachment(txtEasyBankingSource, 0, SWT.RIGHT);
		fd_txtHelloBankingSource.top = new FormAttachment(txtEasyBankingSource, 20);
		fd_txtHelloBankingSource.left = new FormAttachment(lblHelloBanking, 6);
		txtHelloBankingSource.setLayoutData(fd_txtHelloBankingSource);
		
		Button btnBrowseEasyBankingSource = new Button(container, SWT.NONE);
		btnBrowseEasyBankingSource.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialogEasyBanking = new FileDialog(shell);
				fileDialogEasyBanking.open();
				
				String filePath = fileDialogEasyBanking.getFilterPath() + "/" + fileDialogEasyBanking.getFileName();
				
				txtEasyBankingSource.setText(filePath);
			}
		});
		FormData fd_btnBrowseEasyBankingSource = new FormData();
		fd_btnBrowseEasyBankingSource.top = new FormAttachment(0, 35);
		fd_btnBrowseEasyBankingSource.left = new FormAttachment(txtEasyBankingSource, 6);
		btnBrowseEasyBankingSource.setLayoutData(fd_btnBrowseEasyBankingSource);
		btnBrowseEasyBankingSource.setText("Browse");
		
		Button btnBrowseHelloBankingSource = new Button(container, SWT.NONE);
		btnBrowseHelloBankingSource.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog fileDialogHelloBanking = new FileDialog(shell);
				fileDialogHelloBanking.open();
				
				String filePath = fileDialogHelloBanking.getFilterPath() + "/" + fileDialogHelloBanking.getFileName();
				
				txtHelloBankingSource.setText(filePath);
			
			}
		});
		FormData fd_btnBrowseHelloBankingSource = new FormData();
		fd_btnBrowseHelloBankingSource.top = new FormAttachment(txtHelloBankingSource, -4, SWT.TOP);
		fd_btnBrowseHelloBankingSource.left = new FormAttachment(txtHelloBankingSource, 6);
		btnBrowseHelloBankingSource.setLayoutData(fd_btnBrowseHelloBankingSource);
		btnBrowseHelloBankingSource.setText("Browse");

		return container;
	}

	@Override
	protected void okPressed() {
		easyBankingSource = txtEasyBankingSource.getText();
		helloBankingSource = txtHelloBankingSource.getText();
		isOkPressed = true;
		super.okPressed();
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		super.create();
	}

	public boolean isOkPressed() {
		return isOkPressed;
	}

	public void setOkPressed(boolean isOkPressed) {
		this.isOkPressed = isOkPressed;
	}
}
