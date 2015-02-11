package com.mobility.bnpp.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.mobility.bnpp.Activator;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class BNPPPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public BNPPPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Currenty contains all the preferences for BNPP Project Tools");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		DirectoryFieldEditor javaHomePathEditor = new DirectoryFieldEditor(PreferenceConstants.JAVA_HOME_PATH, "&JAVA_HOME Path:",
				getFieldEditorParent());
		
		DirectoryFieldEditor androidHomePathEditor = new DirectoryFieldEditor(PreferenceConstants.ANDROID_HOME_PATH, "&ANDROID_HOME Path:",
				getFieldEditorParent());
		
		DirectoryFieldEditor destinationPathEditor = new DirectoryFieldEditor(PreferenceConstants.DESTINATION_PATH, "&Destination Path:",
				getFieldEditorParent());

		StringFieldEditor extraFolderNameEditor = new StringFieldEditor(PreferenceConstants.EXTRA_FOLDER_NAME, "&Bundle Folder Name:",
				getFieldEditorParent());
		
		StringFieldEditor zippedSourceNameEditor = new StringFieldEditor(PreferenceConstants.ZIPPED_SOURCE_NAME, "&Zipped File Name:",
				getFieldEditorParent());
		
		StringFieldEditor easyBankingAppNameEditor = new StringFieldEditor(PreferenceConstants.EASY_BANKING_APP_NAME, "&Easy Banking App Name:",
				getFieldEditorParent());

		StringFieldEditor helloBankingAppNameEditor = new StringFieldEditor(PreferenceConstants.HELLO_BANKING_APP_NAME, "&Hello Banking App Name:",
				getFieldEditorParent());
		
		StringFieldEditor easyBankingAppVersionEditor = new StringFieldEditor(PreferenceConstants.EASY_BANKING_APP_VERSION, "E&asy Banking App Version:",
				getFieldEditorParent());

		StringFieldEditor helloBankingAppVersionEditor = new StringFieldEditor(PreferenceConstants.HELLO_BANKING_APP_VERSION, "H&ello Banking App Version:",
				getFieldEditorParent());
		
		
		addField(javaHomePathEditor);
		addField(androidHomePathEditor);
		addField(destinationPathEditor);
		
		addField(extraFolderNameEditor);
		addField(zippedSourceNameEditor);
		
		addField(easyBankingAppNameEditor);
		addField(helloBankingAppNameEditor);
		addField(easyBankingAppVersionEditor);
		addField(helloBankingAppVersionEditor);
		
		addField(new BooleanFieldEditor(PreferenceConstants.IS_TRADITIONAL_METHOD_ENABLED, "Traditional Method Enabled", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.IS_CODE_SPLIT_ENABLED, "Code Split Enabled", getFieldEditorParent()));

		/*addField(new RadioGroupFieldEditor(PreferenceConstants.P_CHOICE, "An example of a multiple-choice preference", 1, new String[][] {
				{ "&Choice 1", "choice1" }, { "C&hoice 2", "choice2" } }, getFieldEditorParent()));*/
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}