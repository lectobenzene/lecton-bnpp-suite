package com.mobility.bnpp.sonarreport.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import com.mobility.bnpp.sonarreport.Activator;

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

public class SonarPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public SonarPreferences() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Sonar Report Generation Page");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		{
			DirectoryFieldEditor directoryFieldEditor = new DirectoryFieldEditor(PreferenceConstants.SAVE_TO, "&Save To Location",
					getFieldEditorParent());
			addField(directoryFieldEditor);
		}

		// addField(new StringFieldEditor(PreferenceConstants.CLASS_DATA,
		// "Class Data", 50, StringFieldEditor.VALIDATE_ON_KEY_STROKE,
		// getFieldEditorParent()));
		// addField(new StringFieldEditor(PreferenceConstants.PACKAGE_DATA,
		// "Package Data", 50, StringFieldEditor.VALIDATE_ON_KEY_STROKE,
		// getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.FILE_NAME, "File Name", 50, StringFieldEditor.VALIDATE_ON_KEY_STROKE,
				getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.URL, "Sonar URL", 50, StringFieldEditor.VALIDATE_ON_KEY_STROKE, getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.PROJECT_KEY, "Project Key", 50, StringFieldEditor.VALIDATE_ON_KEY_STROKE,
				getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.CLASS_TITLE, "Table Class Title", 50, StringFieldEditor.VALIDATE_ON_KEY_STROKE,
				getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.PACKAGE_TITLE, "Table Overall Title", 50, StringFieldEditor.VALIDATE_ON_KEY_STROKE,
				getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.CURRENT_OVERALL, "Overall column Title", 50, StringFieldEditor.VALIDATE_ON_KEY_STROKE,
				getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.PREVIOUS_OVERALL, "Previous Overall Title", 50, StringFieldEditor.VALIDATE_ON_KEY_STROKE,
				getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.CLASS_DATA, "Class Data", 25, StringFieldEditor.VALIDATE_ON_KEY_STROKE,
				getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.PACKAGE_DATA, "Package Data", 25, StringFieldEditor.VALIDATE_ON_KEY_STROKE,
				getFieldEditorParent()));
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