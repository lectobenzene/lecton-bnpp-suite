package com.mobility.bnpp.buildautomation.plugincode;

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
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class BackupBuildInputDialog extends Dialog {

	private Shell shell;
	private Text txtDestinationPath;
	private Text txtEasyBankingAppName;
	private Text text_2;
	private Text txtEasyBankingAppVersion;
	private Text txtHelloBankingAppName;
	private Text txtHelloBankingAppVersion;
	private Text text_6;
	
	public String destinationPath;
	
	public String easyBankingAppName;
	public String helloBankingAppName;
	
	public String easyBankingAppVersion;
	public String helloBankingAppVersion;
	
	
	public static boolean isOkPressed = false;
	
	protected BackupBuildInputDialog(Shell parentShell) {
		super(parentShell);
		shell = parentShell;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FormLayout());
		
		Label lblDestination = new Label(container, SWT.NONE);
		FormData fd_lblDestination = new FormData();
		fd_lblDestination.left = new FormAttachment(0, 10);
		fd_lblDestination.top = new FormAttachment(0, 13);
		lblDestination.setLayoutData(fd_lblDestination);
		lblDestination.setText("Destination");
		
		txtDestinationPath = new Text(container, SWT.BORDER);
		FormData fd_txtDestinationPath = new FormData();
		fd_txtDestinationPath.bottom = new FormAttachment(16);
		fd_txtDestinationPath.top = new FormAttachment(0, 10);
		fd_txtDestinationPath.left = new FormAttachment(0, 82);
		txtDestinationPath.setLayoutData(fd_txtDestinationPath);
		
		Button btnBrowse = new Button(container, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog fileDialogDestination = new DirectoryDialog(shell);
				fileDialogDestination.open();
				
				String filePath = fileDialogDestination.getFilterPath();
				
				txtDestinationPath.setText(filePath);
			}
		});
		fd_txtDestinationPath.right = new FormAttachment(100, -79);
		FormData fd_btnBrowse = new FormData();
		fd_btnBrowse.bottom = new FormAttachment(19);
		fd_btnBrowse.left = new FormAttachment(82);
		fd_btnBrowse.top = new FormAttachment(0, 6);
		fd_btnBrowse.right = new FormAttachment(100, -10);
		btnBrowse.setLayoutData(fd_btnBrowse);
		btnBrowse.setText("Browse");
		
		Label lblEasyBanking = new Label(container, SWT.NONE);
		FormData fd_lblEasyBanking = new FormData();
		fd_lblEasyBanking.bottom = new FormAttachment(48);
		fd_lblEasyBanking.right = new FormAttachment(100, -335);
		fd_lblEasyBanking.top = new FormAttachment(0, 73);
		fd_lblEasyBanking.left = new FormAttachment(0, 10);
		lblEasyBanking.setLayoutData(fd_lblEasyBanking);
		lblEasyBanking.setText("Easy Banking");
		
		Label lblHelloBanking = new Label(container, SWT.NONE);
		FormData fd_lblHelloBanking = new FormData();
		fd_lblHelloBanking.right = new FormAttachment(100, -331);
		fd_lblHelloBanking.top = new FormAttachment(0, 102);
		fd_lblHelloBanking.left = new FormAttachment(0, 10);
		lblHelloBanking.setLayoutData(fd_lblHelloBanking);
		lblHelloBanking.setText("Hello Banking");
		
		Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(0, 94);
		fd_label.left = new FormAttachment(0, 10);
		fd_label.right = new FormAttachment(100, -10);
		fd_label.bottom = new FormAttachment(100, -82);
		label.setLayoutData(fd_label);
		
		Label lblAppName = new Label(container, SWT.NONE);
		lblAppName.setAlignment(SWT.CENTER);
		FormData fd_lblAppName = new FormData();
		fd_lblAppName.bottom = new FormAttachment(37);
		fd_lblAppName.top = new FormAttachment(0, 53);
		fd_lblAppName.left = new FormAttachment(25);
		lblAppName.setLayoutData(fd_lblAppName);
		lblAppName.setText("Application Name");
		
		Label lblPeAppVersion = new Label(container, SWT.NONE);
		lblPeAppVersion.setAlignment(SWT.CENTER);
		FormData fd_lblPeAppVersion = new FormData();
		fd_lblPeAppVersion.top = new FormAttachment(0, 53);
		fd_lblPeAppVersion.right = new FormAttachment(100, -10);
		lblPeAppVersion.setLayoutData(fd_lblPeAppVersion);
		lblPeAppVersion.setText("PE App Ver");
		
		txtEasyBankingAppName = new Text(container, SWT.BORDER);
		fd_lblAppName.right = new FormAttachment(100, -148);
		FormData fd_txtEasyBankingAppName = new FormData();
		fd_txtEasyBankingAppName.right = new FormAttachment(lblAppName, 0, SWT.RIGHT);
		fd_txtEasyBankingAppName.left = new FormAttachment(lblAppName, 0, SWT.LEFT);
		fd_txtEasyBankingAppName.top = new FormAttachment(0, 70);
		txtEasyBankingAppName.setLayoutData(fd_txtEasyBankingAppName);
		
		text_2 = new Text(container, SWT.BORDER);
		fd_lblPeAppVersion.bottom = new FormAttachment(38);
		FormData fd_text_2 = new FormData();
		fd_text_2.right = new FormAttachment(btnBrowse, 66);
		fd_text_2.left = new FormAttachment(btnBrowse, 0, SWT.LEFT);
		fd_text_2.top = new FormAttachment(0, 70);
		text_2.setLayoutData(fd_text_2);
		
		txtEasyBankingAppVersion = new Text(container, SWT.BORDER);
		FormData fd_txtEasyBankingAppVersion = new FormData();
		fd_txtEasyBankingAppVersion.right = new FormAttachment(text_2, -2);
		fd_txtEasyBankingAppVersion.left = new FormAttachment(txtEasyBankingAppName, 6);
		fd_txtEasyBankingAppVersion.top = new FormAttachment(0, 70);
		txtEasyBankingAppVersion.setLayoutData(fd_txtEasyBankingAppVersion);
		
		Label lblAppVersion = new Label(container, SWT.NONE);
		lblAppVersion.setAlignment(SWT.CENTER);
		fd_lblPeAppVersion.left = new FormAttachment(81, 4);
		FormData fd_lblAppVersion = new FormData();
		fd_lblAppVersion.bottom = new FormAttachment(38);
		fd_lblAppVersion.top = new FormAttachment(0, 53);
		fd_lblAppVersion.left = new FormAttachment(66);
		fd_lblAppVersion.right = new FormAttachment(100, -79);
		lblAppVersion.setLayoutData(fd_lblAppVersion);
		lblAppVersion.setText("App Ver");
		
		txtHelloBankingAppName = new Text(container, SWT.BORDER);
		FormData fd_txtHelloBankingAppName = new FormData();
		fd_txtHelloBankingAppName.right = new FormAttachment(lblAppName, 0, SWT.RIGHT);
		fd_txtHelloBankingAppName.left = new FormAttachment(lblAppName, 0, SWT.LEFT);
		fd_txtHelloBankingAppName.top = new FormAttachment(0, 102);
		txtHelloBankingAppName.setLayoutData(fd_txtHelloBankingAppName);
		
		txtHelloBankingAppVersion = new Text(container, SWT.BORDER);
		FormData fd_txtHelloBankingAppVersion = new FormData();
		fd_txtHelloBankingAppVersion.left = new FormAttachment(txtHelloBankingAppName, 6);
		fd_txtHelloBankingAppVersion.top = new FormAttachment(0, 102);
		txtHelloBankingAppVersion.setLayoutData(fd_txtHelloBankingAppVersion);
		
		text_6 = new Text(container, SWT.BORDER);
		fd_txtHelloBankingAppVersion.right = new FormAttachment(text_6, -3);
		FormData fd_text_6 = new FormData();
		fd_text_6.left = new FormAttachment(btnBrowse, 0, SWT.LEFT);
		fd_text_6.right = new FormAttachment(btnBrowse, 66);
		fd_text_6.top = new FormAttachment(0, 102);
		text_6.setLayoutData(fd_text_6);
		
		
		txtDestinationPath.setText(destinationPath);
		txtEasyBankingAppName.setText(easyBankingAppName);
		txtHelloBankingAppName.setText(helloBankingAppName);
		txtEasyBankingAppVersion.setText(easyBankingAppVersion);
		txtHelloBankingAppVersion.setText(helloBankingAppVersion);
		
		return container;
	}
	public Text getTxtEasyBankingAppName() {
		return txtEasyBankingAppName;
	}
	public Text getTxtHelloBankingAppName() {
		return txtHelloBankingAppName;
	}
	public Text getTxtEasyBankingAppVersion() {
		return txtEasyBankingAppVersion;
	}
	public Text getTxtHelloBankingAppVersion() {
		return txtHelloBankingAppVersion;
	}
	public Text getText_2() {
		return text_2;
	}
	public Text getTxtDestinationPath() {
		return txtDestinationPath;
	}
	
	public String getDestinationPath(){
		return destinationPath;
	}
	
	public boolean isOkPressed(){
		return isOkPressed;
	}

	
	public void setDestinationPath(String destinationPath){
		this.destinationPath = destinationPath; 
	}
	
	
	
	@Override
	protected void okPressed() {
		destinationPath = txtDestinationPath.getText();
		easyBankingAppName = txtEasyBankingAppName.getText();
		helloBankingAppName = txtHelloBankingAppName.getText();
		easyBankingAppVersion = txtEasyBankingAppVersion.getText();
		helloBankingAppVersion = txtHelloBankingAppVersion.getText();
		
		isOkPressed = true;
		super.okPressed();
	}

	public String getEasyBankingAppName() {
		return easyBankingAppName;
	}

	public void setEasyBankingAppName(String easyBankingAppName) {
		this.easyBankingAppName = easyBankingAppName;
	}

	public String getHelloBankingAppName() {
		return helloBankingAppName;
	}

	public void setHelloBankingAppName(String helloBankingAppName) {
		this.helloBankingAppName = helloBankingAppName;
	}

	public String getEasyBankingAppVersion() {
		return easyBankingAppVersion;
	}

	public void setEasyBankingAppVersion(String easyBankingAppVersion) {
		this.easyBankingAppVersion = easyBankingAppVersion;
	}

	public String getHelloBankingAppVersion() {
		return helloBankingAppVersion;
	}

	public void setHelloBankingAppVersion(String helloBankingAppVersion) {
		this.helloBankingAppVersion = helloBankingAppVersion;
	}
}
