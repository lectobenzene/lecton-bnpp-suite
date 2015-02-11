package com.mobility.bnpp.showfolder;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	/**
	 * The type of OS
	 */
	public static String OS_TYPE;

	// The plug-in ID
	public static final String PLUGIN_ID = "com.mobility.bnpp.showfolder"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		// Set the OS type when the plugin starts
		getOSType();
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Method to check for the OS Type and set the type
	 * 
	 * @return The Type of OS
	 */
	public static String getOSType() {
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().contains("win")) {
			OS_TYPE = "WIN";
		} else if (osName.toLowerCase().contains("mac")) {
			OS_TYPE = "MAC";
		}
		return OS_TYPE;
	}
}
