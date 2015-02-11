package com.mobility.bnpp.sonarreport.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.mobility.bnpp.sonarreport.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		
		store.setDefault(PreferenceConstants.URL, "http://localhost:9000");
		store.setDefault(PreferenceConstants.PROJECT_KEY, "BNPP:BNPP_MR1_2014");
		
		store.setDefault(PreferenceConstants.SAVE_TO, "/Users/Saravana/Desktop");
		store.setDefault(PreferenceConstants.FILE_NAME, "SonarReport%%MMMdd%%");
		store.setDefault(PreferenceConstants.CLASS_TITLE, "SONAR - Standing Order Classes");
		store.setDefault(PreferenceConstants.PACKAGE_TITLE, "SONAR - Overall");
		
		store.setDefault(PreferenceConstants.CURRENT_OVERALL, "MR1 Overall");
		store.setDefault(PreferenceConstants.PREVIOUS_OVERALL, "Previous MR1 Overall");
	}

}
