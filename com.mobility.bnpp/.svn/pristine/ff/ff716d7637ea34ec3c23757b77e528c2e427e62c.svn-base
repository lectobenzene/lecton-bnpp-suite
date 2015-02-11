package com.mobility.bnpp.buildautomation.plugincode;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.mobility.bnpp.Activator;
import com.mobility.bnpp.buildautomation.corecode.MacroClass;
import com.mobility.bnpp.buildautomation.corecode.PackageCopier;
import com.mobility.bnpp.preferences.PreferenceConstants;

public class BuildAutomation extends AbstractHandler {
	
	IPath path = null;
	BuildInputDialog dialog;
	MessageBox alertDialog;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IWorkbenchWindow window =  HandlerUtil.getActiveWorkbenchWindowChecked(event);
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		//System.out.println("is selection empty ? : "+selection.isEmpty());
		if(selection instanceof IStructuredSelection){
			//System.out.println("It is an instance");
			Object[] selectedObjects = ((IStructuredSelection) selection).toArray();
			//System.out.println("array size is "+selectedObjects.length);
			for(Object obj : selectedObjects){
				//System.out.println("Object[] exists");
				if(obj instanceof IJavaProject){
					//System.out.println("Object is instance of IPROJEct");
					path = ((IJavaProject)obj).getProject().getLocation();
				}
			}
		}
		
		
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		PackageCopier.JAVA_HOME = store.getString(PreferenceConstants.JAVA_HOME_PATH);
		PackageCopier.ANDROID_HOME = store.getString(PreferenceConstants.ANDROID_HOME_PATH);
		PackageCopier.DESTINATION_PATH = store.getString(PreferenceConstants.DESTINATION_PATH);
		
		PackageCopier.EXTRA_FOLDER_NAME = store.getString(PreferenceConstants.EXTRA_FOLDER_NAME);
		PackageCopier.ZIPPED_SOURCE_NAME = store.getString(PreferenceConstants.ZIPPED_SOURCE_NAME);
		
		PackageCopier.EASY_BANKING_APP_NAME = store.getString(PreferenceConstants.EASY_BANKING_APP_NAME);
		PackageCopier.HELLO_BANKING_APP_NAME = store.getString(PreferenceConstants.HELLO_BANKING_APP_NAME);
		PackageCopier.EASY_BANKING_APP_VERSION = store.getString(PreferenceConstants.EASY_BANKING_APP_VERSION);
		PackageCopier.HELLO_BANKING_APP_VERSION = store.getString(PreferenceConstants.HELLO_BANKING_APP_VERSION);
		PackageCopier.IS_CODE_SPLIT_ENABLED = store.getBoolean(PreferenceConstants.IS_CODE_SPLIT_ENABLED);
		PackageCopier.IS_TRADITIONAL_METHOD_ENABLED = store.getBoolean(PreferenceConstants.IS_TRADITIONAL_METHOD_ENABLED);
		
		if(PackageCopier.JAVA_HOME.equals("") || PackageCopier.ANDROID_HOME.equals("")){
			 alertDialog = new MessageBox(window.getShell(), SWT.ICON_ERROR | SWT.OK);
			 alertDialog.setText("Path Not Set");
			 alertDialog.setMessage("Set the JAVA_HOME / ANDROID_HOME path in Preferences.");
			 alertDialog.open();
			 return null;
		}
		
		dialog = new BuildInputDialog(window.getShell());
		
		dialog.setDestinationPath(PackageCopier.DESTINATION_PATH);
		dialog.setEasyBankingAppName(PackageCopier.EASY_BANKING_APP_NAME);
		dialog.setHelloBankingAppName(PackageCopier.HELLO_BANKING_APP_NAME);
		dialog.setEasyBankingAppVersion(PackageCopier.EASY_BANKING_APP_VERSION);
		dialog.setHelloBankingAppVersion(PackageCopier.HELLO_BANKING_APP_VERSION);
		dialog.setEasyBankingPEAppVersion(PackageCopier.getPEAppVersion(PackageCopier.EASY_BANKING_APP_VERSION));
		dialog.setHelloBankingPEAppVersion(PackageCopier.getPEAppVersion(PackageCopier.HELLO_BANKING_APP_VERSION));
		
		dialog.setExtraFolderName(MacroClass.convertStringWithDate(PackageCopier.EXTRA_FOLDER_NAME));
		dialog.setZippedSourceCodeName(MacroClass.convertStringWithDate(PackageCopier.ZIPPED_SOURCE_NAME));
		
		dialog.setBuildTypes(MacroClass.getBuildTypesFromConfigFile(path.toOSString()+"/"+PackageCopier.BUILD_TYPE_CONFIG_FILE));
		System.out.println(path.toOSString()+"/"+PackageCopier.BUILD_TYPE_CONFIG_FILE);
		dialog.open();
		
		if(dialog.isOkPressed()){
			Job job = new Job("Build Automation"){

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					
					PackageCopier.SOURCE_PATH = path.toOSString();
					PackageCopier.DESTINATION_PATH = dialog.getDestinationPath();
					PackageCopier.EASY_BANKING_APP_NAME = dialog.getEasyBankingAppName();
					PackageCopier.HELLO_BANKING_APP_NAME = dialog.getHelloBankingAppName();
					PackageCopier.EASY_BANKING_APP_VERSION = dialog.getEasyBankingAppVersion();
					PackageCopier.HELLO_BANKING_APP_VERSION = dialog.getHelloBankingAppVersion();
					
					PackageCopier.EASY_BANKING_PE_APP_VERSION = dialog.getEasyBankingPEAppVersion();
					PackageCopier.HELLO_BANKING_PE_APP_VERSION = dialog.getHelloBankingPEAppVersion();
					
					PackageCopier.EXTRA_FOLDER_NAME = dialog.getExtraFolderName();
					PackageCopier.ZIPPED_SOURCE_NAME = dialog.getZippedSourceCodeName();
					
					
					PackageCopier.IS_MULTI_LANG_BUILD_ENABLED = dialog.isMultiLangBuildEnabled();
					PackageCopier.IS_NORMAL_BUILD_ENABLED = dialog.isNormalBuildEnabled();
					
					PackageCopier.BUILD_TYPE = dialog.getBuildType();
					PackageCopier.runPackageCopier();
					
					return Status.OK_STATUS;
				}
			};
			//job.setUser(true);
			job.setPriority(Job.LONG);
			job.schedule();
		}

		
		
		
		return null;
	}

}
