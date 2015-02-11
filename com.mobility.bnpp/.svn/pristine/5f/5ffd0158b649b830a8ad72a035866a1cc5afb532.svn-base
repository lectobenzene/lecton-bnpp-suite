package com.mobility.bnpp.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.mobility.bnpp.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	// Default application names
	public final static String EASY_BANKING_APP_NAME = "EasyBanking_Android_v";
	public final static String HELLO_BANKING_APP_NAME = "Hello bank!_Android_v";
	
	// Default application versions
	public final static String EASY_BANKING_APP_VERSION = "4.0.0";
	public final static String HELLO_BANKING_APP_VERSION = "3.0.0";

	// Default folder and zip file names
	public final static String EXTRA_FOLDER_NAME = "Essentials";
	public final static String ZIPPED_SOURCE_NAME = "Android_Source_Code";
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		
		// Setting the default value for the application names
		store.setDefault(PreferenceConstants.EASY_BANKING_APP_NAME, EASY_BANKING_APP_NAME);
		store.setDefault(PreferenceConstants.HELLO_BANKING_APP_NAME, HELLO_BANKING_APP_NAME);
		
		/*
		 * Setting the default value for the extra folder name,i.e The folder where all the deliverables
		 * are bundled together.
		 */
		store.setDefault(PreferenceConstants.EXTRA_FOLDER_NAME, EXTRA_FOLDER_NAME);
		store.setDefault(PreferenceConstants.ZIPPED_SOURCE_NAME, ZIPPED_SOURCE_NAME);
		
		// Setting the default value for the application versions
		store.setDefault(PreferenceConstants.EASY_BANKING_APP_VERSION, EASY_BANKING_APP_VERSION);
		store.setDefault(PreferenceConstants.HELLO_BANKING_APP_VERSION, HELLO_BANKING_APP_VERSION);
		
		store.setDefault(PreferenceConstants.IS_CODE_SPLIT_ENABLED, true);
		store.setDefault(PreferenceConstants.IS_TRADITIONAL_METHOD_ENABLED, false);
	}

}
