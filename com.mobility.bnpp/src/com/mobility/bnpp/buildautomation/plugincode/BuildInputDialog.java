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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

import com.mobility.bnpp.buildautomation.corecode.PackageCopier;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Combo;

public class BuildInputDialog extends Dialog {

	private Shell shell;
	private Text txtDestinationPath;
	private Text txtEasyBankingAppName;
	private Text txtEasyBankingPEAppVersion;
	private Text txtEasyBankingAppVersion;
	private Text txtHelloBankingAppName;
	private Text txtHelloBankingAppVersion;
	private Text txtHelloBankingPEAppVersion;
	
	private Text txtExtraFolderName;
	private Text txtZippedSourceCodeName;

	private Combo combo;
	
	private Button btnGenerateMultilangBuild;
	private Button btnGenerateNormalBuild;

	public String destinationPath;
	
	public String easyBankingAppName;
	public String helloBankingAppName;
	
	public String easyBankingAppVersion;
	public String helloBankingAppVersion;
	
	public String easyBankingPEAppVersion;
	public String helloBankingPEAppVersion;
	
	public String extraFolderName;
	public String zippedSourceCodeName;
	
	public boolean isMultiLangBuildEnabled;
	public boolean isNormalBuildEnabled;
	
	public boolean isOkPressed = false;
	
	/**
	 * Array containing the types of build.
	 * This is obtained from the BUILD_TYPE_CONFIG_FILE
	 */
	public String[] buildTypes = null;
	
	public String buildType = null;
	
	protected BuildInputDialog(Shell parentShell) {
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
		fd_lblDestination.top = new FormAttachment(0, 26);
		lblDestination.setLayoutData(fd_lblDestination);
		lblDestination.setText("Destination");
		
		txtDestinationPath = new Text(container, SWT.BORDER);
		FormData fd_txtDestinationPath = new FormData();
		fd_txtDestinationPath.left = new FormAttachment(lblDestination, 3);
		fd_txtDestinationPath.top = new FormAttachment(lblDestination, -3, SWT.TOP);
		txtDestinationPath.setLayoutData(fd_txtDestinationPath);
		
		Button btnBrowse = new Button(container, SWT.NONE);
		fd_txtDestinationPath.right = new FormAttachment(100, -82);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog fileDialogDestination = new DirectoryDialog(shell);
				fileDialogDestination.open();
				
				String filePath = fileDialogDestination.getFilterPath();
				
				txtDestinationPath.setText(filePath);
			}
		});
		FormData fd_btnBrowse = new FormData();
		fd_btnBrowse.top = new FormAttachment(lblDestination, -7, SWT.TOP);
		fd_btnBrowse.left = new FormAttachment(txtDestinationPath, 6);
		fd_btnBrowse.right = new FormAttachment(100, -10);
		btnBrowse.setLayoutData(fd_btnBrowse);
		btnBrowse.setText("Browse");
		
		
		txtDestinationPath.setText(destinationPath);
		
		Group grpEasyBanking = new Group(container, SWT.SHADOW_ETCHED_IN);
		grpEasyBanking.setText("Easy Banking");
		FormData fd_grpEasyBanking = new FormData();
		fd_grpEasyBanking.top = new FormAttachment(btnBrowse, 6);
		fd_grpEasyBanking.right = new FormAttachment(btnBrowse, 0, SWT.RIGHT);
		fd_grpEasyBanking.left = new FormAttachment(0, 10);
		grpEasyBanking.setLayoutData(fd_grpEasyBanking);
		
		Group grpHelloBanking = new Group(container, SWT.NONE);
		fd_grpEasyBanking.bottom = new FormAttachment(100, -251);
		
		Label lblApplicationName = new Label(grpEasyBanking, SWT.NONE);
		lblApplicationName.setBounds(10, 13, 99, 14);
		lblApplicationName.setText("Application Name");
		
		txtEasyBankingAppName = new Text(grpEasyBanking, SWT.BORDER);
		txtEasyBankingAppName.setBounds(115, 10, 272, 19);
		txtEasyBankingAppName.setText(easyBankingAppName);
		
		Label lblAppVersion = new Label(grpEasyBanking, SWT.NONE);
		lblAppVersion.setBounds(10, 50, 99, 14);
		lblAppVersion.setText("App Version");
		
		txtEasyBankingAppVersion = new Text(grpEasyBanking, SWT.BORDER);
		
		txtEasyBankingAppVersion.setBounds(115, 47, 64, 19);
		txtEasyBankingAppVersion.setText(easyBankingAppVersion);
		
		txtEasyBankingPEAppVersion = new Text(grpEasyBanking, SWT.BORDER);
		txtEasyBankingPEAppVersion.setBounds(321, 47, 66, 19);
		
		Label lblPeAppVersion = new Label(grpEasyBanking, SWT.NONE);
		lblPeAppVersion.setBounds(230, 50, 85, 14);
		lblPeAppVersion.setText("PE App Version");
		grpHelloBanking.setText("Hello Banking");
		FormData fd_grpHelloBanking = new FormData();
		fd_grpHelloBanking.top = new FormAttachment(grpEasyBanking, 6);
		fd_grpHelloBanking.right = new FormAttachment(btnBrowse, 0, SWT.RIGHT);
		fd_grpHelloBanking.left = new FormAttachment(0, 10);
		grpHelloBanking.setLayoutData(fd_grpHelloBanking);
		
		Label lblApplicationName_1 = new Label(grpHelloBanking, SWT.NONE);
		lblApplicationName_1.setBounds(10, 10, 99, 14);
		lblApplicationName_1.setText("Application Name");
		
		txtHelloBankingAppName = new Text(grpHelloBanking, SWT.BORDER);
		txtHelloBankingAppName.setBounds(115, 7, 272, 19);
		txtHelloBankingAppName.setText(helloBankingAppName);
		
		Label lblAppVersion_1 = new Label(grpHelloBanking, SWT.NONE);
		lblAppVersion_1.setBounds(10, 50, 99, 14);
		lblAppVersion_1.setText("App Version");
		
		txtHelloBankingAppVersion = new Text(grpHelloBanking, SWT.BORDER);
		txtHelloBankingAppVersion.setBounds(115, 47, 63, 19);
		txtHelloBankingAppVersion.setText(helloBankingAppVersion);
		
		txtHelloBankingPEAppVersion = new Text(grpHelloBanking, SWT.BORDER);
		txtHelloBankingPEAppVersion.setBounds(321, 47, 66, 19);
		
		Label lblPeAppVersion_1 = new Label(grpHelloBanking, SWT.NONE);
		lblPeAppVersion_1.setBounds(230, 50, 85, 14);
		lblPeAppVersion_1.setText("PE App Version");
		
		
		txtEasyBankingPEAppVersion.setText(easyBankingPEAppVersion);
		txtHelloBankingPEAppVersion.setText(helloBankingPEAppVersion);
		
		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		fd_grpHelloBanking.bottom = new FormAttachment(100, -154);
		FormData fd_tabFolder = new FormData();
		fd_tabFolder.top = new FormAttachment(grpHelloBanking, 6);
		fd_tabFolder.bottom = new FormAttachment(100, -10);
		fd_tabFolder.right = new FormAttachment(btnBrowse, 0, SWT.RIGHT);
		fd_tabFolder.left = new FormAttachment(0, 10);
		tabFolder.setLayoutData(fd_tabFolder);
		
		
		TabItem tbtmFileName = new TabItem(tabFolder, SWT.NONE);
		tbtmFileName.setText("File Name");
		
		TabItem tabBuildOptions = new TabItem(tabFolder, SWT.NONE);
		tabBuildOptions.setText("Build Options");
		
		Group group = new Group(tabFolder, SWT.NONE);
		tbtmFileName.setControl(group);
		
		
		Group buildGroup = new Group(tabFolder, SWT.NONE);
		tabBuildOptions.setControl(buildGroup);
		
	    btnGenerateNormalBuild = new Button(buildGroup, SWT.CHECK);
	    
	    // This checkBox is always selection, since we always require a normal build. 
	    btnGenerateNormalBuild.setSelection(true);
		
	    btnGenerateNormalBuild.setBounds(10, 10, 143, 18);
		btnGenerateNormalBuild.setText("Generate Normal Build");
		
	    btnGenerateMultilangBuild = new Button(buildGroup, SWT.CHECK);
		btnGenerateMultilangBuild.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnGenerateMultilangBuild.setBounds(211, 10, 162, 18);
		btnGenerateMultilangBuild.setText("Generate Multi-Lang Build");
		
		Label lblTypeOfBuild = new Label(buildGroup, SWT.NONE);
		lblTypeOfBuild.setBounds(10, 58, 74, 14);
		lblTypeOfBuild.setText("Type of Build");
		
		combo = new Combo(buildGroup, SWT.NONE);
		
		if(buildTypes!=null){
			combo.setItems(buildTypes);
		}else{
			combo.setItems(new String[] {"QA", "PILOT", "PROD"});
		}
		
		// By default first item is select. Idealy it is "QA"
		combo.select(0);
		combo.setBounds(90, 54, 105, 22);
		
		combo.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
					buildType = combo.getItem(combo.getSelectionIndex());
			}
		});
		
		Label lblBundleFolderName = new Label(group, SWT.NONE);
		lblBundleFolderName.setBounds(10, 13, 122, 14);
		lblBundleFolderName.setText("Bundle Folder Name : ");
		
		txtExtraFolderName = new Text(group, SWT.BORDER);
		txtExtraFolderName.setBounds(170, 10, 203, 19);
		
		Label lblZippedSourceCode = new Label(group, SWT.NONE);
		lblZippedSourceCode.setBounds(10, 40, 152, 14);
		lblZippedSourceCode.setText("Zipped Source Code Name :");
		
		txtZippedSourceCodeName = new Text(group, SWT.BORDER);
		txtZippedSourceCodeName.setBounds(170, 35, 203, 19);
		
		/*
		 * Manual code : Sets the text for txtExtraFolderName
		 */
		txtExtraFolderName.setText(extraFolderName);
		
		/*
		 * Manual code : Sets the text for txtZippedSourceCode
		 */
		txtZippedSourceCodeName.setText(zippedSourceCodeName);
		
		
		txtEasyBankingAppVersion.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				txtEasyBankingPEAppVersion.setText(PackageCopier.getPEAppVersion(txtEasyBankingAppVersion.getText()));
				txtHelloBankingAppVersion.setText("3"+txtEasyBankingAppVersion.getText().substring(1));
			}
		});
		txtHelloBankingAppVersion.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				txtHelloBankingPEAppVersion.setText(PackageCopier.getPEAppVersion(txtHelloBankingAppVersion.getText()));
			}
		});
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
		return txtEasyBankingPEAppVersion;
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
		easyBankingPEAppVersion = txtEasyBankingPEAppVersion.getText();
		helloBankingPEAppVersion = txtHelloBankingPEAppVersion.getText();
		
		extraFolderName = txtExtraFolderName.getText();
		zippedSourceCodeName = txtZippedSourceCodeName.getText();
		isMultiLangBuildEnabled = btnGenerateMultilangBuild.getSelection();
		isNormalBuildEnabled = btnGenerateNormalBuild.getSelection();
		
		buildType = combo.getItem(combo.getSelectionIndex());
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

	public String getEasyBankingPEAppVersion() {
		return easyBankingPEAppVersion;
	}

	public void setEasyBankingPEAppVersion(String easyBankingPEAppVersion) {
		this.easyBankingPEAppVersion = easyBankingPEAppVersion;
	}

	public String getHelloBankingPEAppVersion() {
		return helloBankingPEAppVersion;
	}

	public void setHelloBankingPEAppVersion(String helloBankingPEAppVersion) {
		this.helloBankingPEAppVersion = helloBankingPEAppVersion;
	}
		
	public Text getTxtExtraFolderName() {
		return txtExtraFolderName;
	}
	public Text getTxtZippedSourceCodeName() {
		return txtZippedSourceCodeName;
	}

	public String getExtraFolderName() {
		return extraFolderName;
	}

	public void setExtraFolderName(String extraFolderName) {
		this.extraFolderName = extraFolderName;
	}

	public String getZippedSourceCodeName() {
		return zippedSourceCodeName;
	}

	public void setZippedSourceCodeName(String zippedSourceCodeName) {
		this.zippedSourceCodeName = zippedSourceCodeName;
	}

	public boolean isMultiLangBuildEnabled() {
		return isMultiLangBuildEnabled;
	}

	public void setMultiLangBuildEnabled(boolean isMultiLangBuildEnabled) {
		this.isMultiLangBuildEnabled = isMultiLangBuildEnabled;
	}

	public boolean isNormalBuildEnabled() {
		return isNormalBuildEnabled;
	}

	public void setNormalBuildEnabled(boolean isNormalBuildEnabled) {
		this.isNormalBuildEnabled = isNormalBuildEnabled;
	}

	public String[] getBuildTypes() {
		return buildTypes;
	}

	public void setBuildTypes(String[] buildTypes) {
		this.buildTypes = buildTypes;
	}

	public String getBuildType() {
		return buildType;
	}

	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}
}
