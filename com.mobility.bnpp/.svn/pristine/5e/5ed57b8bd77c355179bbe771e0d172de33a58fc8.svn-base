package com.mobility.bnpp.buildautomation.corecode;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.Task;


/**
 * Class used for running ANT on build.xml and generate the APK
 * @author Saravana
 *
 */
public class AntRunner extends Task{

	/*
	 *  Variables in the Build.xml whose values are set in the code
	 *  and not taken from the config.xml
	 */
	private static final String APP_NAME_POINTER = "env.app_name";
	private static final String ANDROID_HOME = "env.ANDROID_HOME";
	private static final String JAVA_HOME = "env.JAVA_HOME";
	
	
	private String appName;
	private String buildFilePath;
	
	/**
	 * Constructor for the class
	 * @param appName The name of the application reflected in the file name of APK
	 * @param buildFilePath The path to the build.xml file
	 */
	public AntRunner(String appName, String buildFilePath){
		this.appName = appName;
		this.buildFilePath = buildFilePath;
	}
	
	/**
	 * Method to run generated the APK by running ANT
	 */
	public void runAntBuild(){
		Project project = new Project();

		project.setUserProperty(APP_NAME_POINTER, appName);
		project.setUserProperty(JAVA_HOME, PackageCopier.JAVA_HOME);
		project.setUserProperty(ANDROID_HOME, PackageCopier.ANDROID_HOME);
		project.setUserProperty("user.dir", buildFilePath);		
		
		File buildFile = new File(buildFilePath+"/build.xml");
		ProjectHelper.configureProject(project, buildFile);
		
		
		// Clean is not necessary since, there would be no BIN folder initially.
		// project.executeTarget("clean");
		// System.out.println("Clean done");
		
		project.executeTarget("rel");
		System.out.println("REL done");
	}
	
	/**
	 * Main method for the class used as for DEBUG. Can be removed.
	 * @param args
	 */
	public static void main(String[] args) {
		Project project = new Project();

		project.setUserProperty("env.app_name", "Hello_Banking_1");
		String buildFilePath = "/Users/Saravana/Desktop/MacroSource/HelloBanking/build.xml";
		
		File buildFile = new File(buildFilePath);
		ProjectHelper.configureProject(project, buildFile);
		
		project.executeTarget("clean");
		System.out.println("Clean done");
		
		project.executeTarget("rel");
		System.out.println("REL done");
	}

	@Override
	public void execute() throws BuildException {
		super.execute();
	}

}

