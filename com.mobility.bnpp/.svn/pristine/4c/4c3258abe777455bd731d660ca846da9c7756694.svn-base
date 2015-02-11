package com.mobility.bnpp.buildautomation.corecode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

import com.mobility.bnpp.integratemultilang.ExcelParser;

/**
 * Main class to generate build for EasyBanking and HelloBank android
 * application
 * 
 * @author Saravana
 * 
 */
public class PackageCopier {

	public static String JAVA_HOME = null;
	public static String ANDROID_HOME = null;

	public static String SOURCE_PATH = null;
	public static String DESTINATION_PATH = null;

	public static String EXTRA_FOLDER_NAME = null;
	public static String ZIPPED_SOURCE_NAME = null;

	public static String EASY_BANKING_APP_NAME = null;
	public static String HELLO_BANKING_APP_NAME = null;

	public static String EASY_BANKING_APP_VERSION = null;
	public static String HELLO_BANKING_APP_VERSION = null;

	public static String EASY_BANKING_PE_APP_VERSION = null;
	public static String HELLO_BANKING_PE_APP_VERSION = null;

	/**
	 * This denotes the type of build, i.e, QA, PILOT, etc
	 */
	public static String BUILD_TYPE = null;
	
	
	/**
	 * Variable that determines, if EasyBanking and Hellobanking code should be
	 * split. "isZenaApp" method.
	 */
	public static boolean IS_CODE_SPLIT_ENABLED;

	/**
	 * If enabled, the traditional method of giving build is done, where all
	 * occurrences of com.bnpp.easybanking is replaced by com.bnpp.hellobank
	 */
	public static boolean IS_TRADITIONAL_METHOD_ENABLED;

	
	/**
	 * If enabled, the normal signed build is generated. This is true by default, but
	 * can be omitted while giving the build.
	 */
	public static boolean IS_NORMAL_BUILD_ENABLED;
	
	/**
	 * If enabled, an extra build is given where the entire strings.xml contains
	 * only the ID and no human readable texts. This is for BAT tests alone.
	 */
	public static boolean IS_MULTI_LANG_BUILD_ENABLED;

	public final static String FOLDER_NAME_EASYBANKING = "EasyBanking";
	public final static String FOLDER_NAME_HELLOBANKING = "HelloBanking";
	public final static String FOLDER_NAME_FINTRO = "Fintro";

	public final static String BUILD_FILE = "build.xml";
	public final static String CONFIG_FILE = "config.xml";
	
	/**
	 * This is the file that contains that list of Types Of Builds and also
	 * that determines the type of build.
	 */
	public final static String BUILD_TYPE_CONFIG_FILE = "assets/config.txt";

	/**
	 * This file contains the default values of the parameters. If the parameters are null in the java code, 
	 * then the values for the parameters are taken from the eclipse_plugin_config file. This is to support 
	 * the Jenkins methodology of providing builds. This file should be placed in the same folder as the
	 * runnable jar file of this project.
	 */
	public final static String ECLIPSE_PLUGIN_CONFIG_FILE = "eclipse_plugin_config.xml";
	
	/**
	 * This string contains the content of the ECLIPSE_PLUGIN_CONFIG_FILE, so that further parsing of
	 * parameters can be done effectively.
	 */
	public static String ECLIPSE_PLUGIN_CONFIG_FILE_CONTENT = null;
	
	public final static String FOLDER_MULTI_LANG_BUILDS = "MultiLang_Build";
	/*
	 * Constants to denote to which project the Folder or File under analysis
	 * belong to, especially during the walkFileTree
	 */
	public final static int PROJECT_EASYBANKING = 0;
	public final static int PROJECT_HELLOBANKING = 1;
	public final static int PROJECT_FINTRO = 2;

	/**
	 * Used only for creation of directories. This is an alternative for the
	 * method Files.createDirectories();
	 */
	static File folderCreationFile;

	static ZipOutputStream zipOutputStream;

	static String appName;
	static String buildFilePath;;
	static AntRunner antRunner;
	static Path apkPath;
	static Path newApkPath;
	
	
	/**
	 * Converts the AppVersion to PEAppVersion [x.y.z is converted to xyz]
	 * 
	 * @param appVersion
	 *            The version of the application denoted by the constant
	 *            EASY_BANKING_APP_VERSION or HELLO_BANKING_APP_VERSION
	 * @return peAppVersion The string where the "." is removed.
	 */
	public static String getPEAppVersion(String appVersion) {
		String peAppVersion;
		peAppVersion = appVersion.replace(".", "");
		return peAppVersion;
	}

	public static void main(String[] args) throws IOException {

		takeParametersFromConfigFileIfNull();
		
		switch(args.length){
		
		case 17:
			PackageCopier.JAVA_HOME = args[16];
		case 16:
			PackageCopier.ANDROID_HOME = args[15];
		case 15:
			PackageCopier.IS_MULTI_LANG_BUILD_ENABLED = Boolean.parseBoolean(args[14]);
		case 14:
			PackageCopier.IS_NORMAL_BUILD_ENABLED = Boolean.parseBoolean(args[13]);
		case 13:
			PackageCopier.IS_TRADITIONAL_METHOD_ENABLED = Boolean.parseBoolean(args[12]);
		case 12:
			PackageCopier.IS_CODE_SPLIT_ENABLED = Boolean.parseBoolean(args[11]);
		case 11:
			PackageCopier.BUILD_TYPE = args[10];
		case 10:
			PackageCopier.ZIPPED_SOURCE_NAME = args[9];
		case 9:
			PackageCopier.EXTRA_FOLDER_NAME = args[8];
		case 8:
			PackageCopier.HELLO_BANKING_PE_APP_VERSION = args[7];
		case 7:
			PackageCopier.EASY_BANKING_PE_APP_VERSION = args[6];
		case 6:
			PackageCopier.HELLO_BANKING_APP_VERSION = args[5];
		case 5:
			PackageCopier.EASY_BANKING_APP_VERSION = args[4];
		case 4:
			PackageCopier.HELLO_BANKING_APP_NAME = args[3];
		case 3:
			PackageCopier.EASY_BANKING_APP_NAME = args[2];
		case 2:
			PackageCopier.DESTINATION_PATH = args[1];
		case 1:
			PackageCopier.SOURCE_PATH = args[0];
			break;
		default:
			System.out.println("RUN without ARGUMENTS");
		}
		
		runPackageCopier();

	}

	/**
	 * This method initializes all the NULL fields from the XML file : ECLIPSE_PLUGIN_CONFIG_FILE
	 */
	private static void takeParametersFromConfigFileIfNull() {
		File eclipseConfigFile = new File(ECLIPSE_PLUGIN_CONFIG_FILE);
		
		// Check if the file exits, or throw ERROR.
		if (eclipseConfigFile.exists()) {
			FileReader reader;
			try {
				reader = new FileReader(eclipseConfigFile);
				
				char[] buffer = new char[(int) eclipseConfigFile.length()];
				reader.read(buffer);
				
				ECLIPSE_PLUGIN_CONFIG_FILE_CONTENT = String.valueOf(buffer);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {
			System.out.println("ERROR : File does not exist! [Provide proper Folder structure]");
		}
			
		
		ANDROID_HOME = getParameterFromConfigFile("AndroidHome");
		JAVA_HOME = getParameterFromConfigFile("JavaHome");
		
		SOURCE_PATH = getParameterFromConfigFile("SourcePath");
		DESTINATION_PATH = getParameterFromConfigFile("DestinationPath");
		
		EXTRA_FOLDER_NAME = MacroClass.convertStringWithDate(getParameterFromConfigFile("ExtraFolderName"));
		ZIPPED_SOURCE_NAME = MacroClass.convertStringWithDate(getParameterFromConfigFile("ZippedSourceName"));
		
		EASY_BANKING_APP_NAME = getParameterFromConfigFile("EasyBankingAppName");
		HELLO_BANKING_APP_NAME = getParameterFromConfigFile("HelloBankingAppName");
		EASY_BANKING_APP_VERSION = getParameterFromConfigFile("EasyBankingAppVersion");
		HELLO_BANKING_APP_VERSION = getParameterFromConfigFile("HelloBankingAppVersion");
		EASY_BANKING_PE_APP_VERSION = getParameterFromConfigFile("EasyBankingPEAppVersion");
		HELLO_BANKING_PE_APP_VERSION = getParameterFromConfigFile("HelloBankingPEAppVersion");
		BUILD_TYPE = getParameterFromConfigFile("BuildType");
		
		if(getParameterFromConfigFile("IsCodeSplitEnabled").equalsIgnoreCase("true")){
			IS_CODE_SPLIT_ENABLED = true;
		}else if(getParameterFromConfigFile("IsCodeSplitEnabled").equalsIgnoreCase("false")){
			IS_CODE_SPLIT_ENABLED = false;
		}else{
			System.out.println("Parameter for IsCodeSplitEnabled is improper");
		}
		
		if(getParameterFromConfigFile("IsTraditionalMethodEnabled").equalsIgnoreCase("true")){
			IS_TRADITIONAL_METHOD_ENABLED = true;
		}else if(getParameterFromConfigFile("IsTraditionalMethodEnabled").equalsIgnoreCase("false")){
			IS_TRADITIONAL_METHOD_ENABLED = false;
		}else{
			System.out.println("Parameter for IsTraditionalMethodEnabled is improper");
		}	
		
		if(getParameterFromConfigFile("IsNomalBuildEnabled").equalsIgnoreCase("true")){
			IS_NORMAL_BUILD_ENABLED = true;
		}else if(getParameterFromConfigFile("IsNomalBuildEnabled").equalsIgnoreCase("false")){
			IS_NORMAL_BUILD_ENABLED = false;
		}else{
			System.out.println("Parameter for IsNomalBuildEnabled is improper");
		}	
		
		if(getParameterFromConfigFile("IsMultiLangBuildEnabled").equalsIgnoreCase("true")){
			IS_MULTI_LANG_BUILD_ENABLED = true;
		}else if(getParameterFromConfigFile("IsMultiLangBuildEnabled").equalsIgnoreCase("false")){
			IS_MULTI_LANG_BUILD_ENABLED = false;
		}else{
			System.out.println("Parameter for IsMultiLangBuildEnabled is improper");
		}	
		
		
		System.out.println(ANDROID_HOME);
		System.out.println(JAVA_HOME);
		System.out.println(SOURCE_PATH);
		System.out.println(DESTINATION_PATH);
		System.out.println(EXTRA_FOLDER_NAME);
		System.out.println(ZIPPED_SOURCE_NAME);
		System.out.println(EASY_BANKING_APP_NAME);
		System.out.println(HELLO_BANKING_APP_NAME);
		System.out.println(EASY_BANKING_APP_VERSION);
		System.out.println(HELLO_BANKING_APP_VERSION);
		System.out.println(EASY_BANKING_PE_APP_VERSION);
		System.out.println(HELLO_BANKING_PE_APP_VERSION);
		System.out.println(IS_CODE_SPLIT_ENABLED);
		System.out.println(IS_TRADITIONAL_METHOD_ENABLED);
		System.out.println(IS_NORMAL_BUILD_ENABLED);
		System.out.println(IS_MULTI_LANG_BUILD_ENABLED);
		System.out.println(BUILD_TYPE);

		
	}

	private static String getParameterFromConfigFile(String parameter) {
		
		String regex = "<parameter name=\""+parameter+"\">([^<]*)</parameter>";
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(ECLIPSE_PLUGIN_CONFIG_FILE_CONTENT);
		
		if(matcher.find()){
			return matcher.group(1);
		}
		return null;
	}

	public static void runPackageCopier() {

		DESTINATION_PATH = DESTINATION_PATH + "/" + EXTRA_FOLDER_NAME;

		Path source = Paths.get(SOURCE_PATH);
		Path target = Paths.get(DESTINATION_PATH);

		try {

			double initialTimeTaken = System.currentTimeMillis();

			/*
			 * Deletes the Folder if it was already present else creates the
			 * directory.
			 */
			System.out.println(target);
			if (Files.exists(target, LinkOption.NOFOLLOW_LINKS)) {
				Files.walkFileTree(target, new DeleteFolderFilter());

				folderCreationFile = new File(target.toString());
				folderCreationFile.mkdirs();
			} else {
				folderCreationFile = new File(target.toString());
				folderCreationFile.mkdirs();

			}

			/*
			 * Copies all the files that does not require modification except a
			 * few. Does not involve the SRC or RES folders.
			 */
			Files.walkFileTree(source, new CopyFileFilter(source, target, PROJECT_EASYBANKING));
			Files.walkFileTree(source, new CopyFileFilter(source, target, PROJECT_HELLOBANKING));

			/*
			 * Copies the SRC folder
			 */
			Path sourceJava = source.resolve("src");
			Files.walkFileTree(sourceJava, new SplitSourceFilter(sourceJava, target));

			/*
			 * Copies the RES folder for EasyBanking
			 */
			Path sourceResource = source.resolve("res");
			Files.walkFileTree(sourceResource, new SplitResourceFilter(sourceResource, target, PROJECT_EASYBANKING));

			/*
			 * Copies the RES_HELLOBANK folder for HelloBanking
			 */
			sourceResource = source.resolve("res_hellobank");
			Files.walkFileTree(sourceResource, new SplitResourceFilter(sourceResource, target, PROJECT_HELLOBANKING));

			double finalTimeTaken = System.currentTimeMillis() - initialTimeTaken;
			System.out.println("Time Taken : " + finalTimeTaken / 1000 + "s");

			/*
			 * Changes the folder structure from
			 */
			if (PackageCopier.IS_TRADITIONAL_METHOD_ENABLED) {
				sourceResource = Paths.get(DESTINATION_PATH + "/" + FOLDER_NAME_HELLOBANKING + "/src/com/bnpp/easybanking");
				Path helloBankingTarget = sourceResource.resolveSibling("hellobank");
				Files.move(sourceResource, helloBankingTarget, StandardCopyOption.REPLACE_EXISTING);
			}

			finalTimeTaken = System.currentTimeMillis() - initialTimeTaken;
			System.out.println("Time Taken : " + finalTimeTaken / 1000 + "s");

			
			if(IS_NORMAL_BUILD_ENABLED){
				
				/*
				 * Runs the Ant for EasyBanking application
				 */
			    appName = EASY_BANKING_APP_NAME + EASY_BANKING_APP_VERSION;
				buildFilePath = DESTINATION_PATH + "/" + FOLDER_NAME_EASYBANKING;
				antRunner = new AntRunner(appName, buildFilePath);
				antRunner.runAntBuild();
				
				finalTimeTaken = System.currentTimeMillis() - initialTimeTaken;
				System.out.println("Time Taken : " + finalTimeTaken / 1000 + "s");
				
				/*
				 * Runs the Ant for HelloBanking application
				 */
				appName = HELLO_BANKING_APP_NAME + HELLO_BANKING_APP_VERSION;
				buildFilePath = DESTINATION_PATH + "/" + FOLDER_NAME_HELLOBANKING;
				antRunner = new AntRunner(appName, buildFilePath);
				antRunner.runAntBuild();
				
				finalTimeTaken = System.currentTimeMillis() - initialTimeTaken;
				System.out.println("Time Taken : " + finalTimeTaken / 1000 + "s");
				
				/*
				 * Move the generated APK from bin folder to the DESTINATION_PATH.
				 */
				apkPath = Paths.get(DESTINATION_PATH + "/" + FOLDER_NAME_EASYBANKING + "/bin" + "/" + EASY_BANKING_APP_NAME
						+ EASY_BANKING_APP_VERSION + ".apk");
				newApkPath = Paths.get(DESTINATION_PATH + "/" + EASY_BANKING_APP_NAME + EASY_BANKING_APP_VERSION + ".apk");
				
				System.out.println("EasyBanking APK location : " + apkPath);
				System.out.println("EasyBanking NEW APK location : " + newApkPath);
				
				Files.move(apkPath, newApkPath, StandardCopyOption.REPLACE_EXISTING);
				
				apkPath = Paths.get(DESTINATION_PATH + "/" + FOLDER_NAME_HELLOBANKING + "/bin" + "/" + HELLO_BANKING_APP_NAME + HELLO_BANKING_APP_VERSION
						+ ".apk");
				newApkPath = Paths.get(DESTINATION_PATH + "/" + HELLO_BANKING_APP_NAME + HELLO_BANKING_APP_VERSION + ".apk");
				
				System.out.println("HelloBanking APK location : " + apkPath);
				System.out.println("HelloBanking NEW APK location : " + newApkPath);
				
				Files.move(apkPath, newApkPath, StandardCopyOption.REPLACE_EXISTING);
				
				finalTimeTaken = System.currentTimeMillis() - initialTimeTaken;
				System.out.println("Time Taken : " + finalTimeTaken / 1000 + "s");
			}

			/*
			 * ZIPs the Source Folder and stores in the DESTINATION_PATH.
			 */
			FileOutputStream fileOutputStream = new FileOutputStream(DESTINATION_PATH + "/" + ZIPPED_SOURCE_NAME + ".zip");
			zipOutputStream = new ZipOutputStream(fileOutputStream);

			Files.walkFileTree(source, new ZipFolderFilter(source));

			zipOutputStream.close();

			finalTimeTaken = System.currentTimeMillis() - initialTimeTaken;
			System.out.println("Time Taken : " + finalTimeTaken / 1000 + "s");

			/*
			 * MULTI_LANG BUILD for BAT Tests
			 */
			System.out.println("IS_MULTI_LANG_BUILD_ENABLED - - "+IS_MULTI_LANG_BUILD_ENABLED);
			if (IS_MULTI_LANG_BUILD_ENABLED) {

				/*
				 * Multi-Lan APK generation for Easybanking
				 */

				// Set the path of the EasyBanking folder
				source = Paths.get(DESTINATION_PATH + "/" + FOLDER_NAME_EASYBANKING);
				System.out.println(source);

				// Create object of ExcelParser to call the method to change strings.xml.
				ExcelParser excel = new ExcelParser();
				excel.updateMultiLangWithRawID(source);

				/*
				 * Runs the Ant for EasyBanking application
				 */
				appName = EASY_BANKING_APP_NAME + EASY_BANKING_APP_VERSION;
				buildFilePath = DESTINATION_PATH + "/" + FOLDER_NAME_EASYBANKING;
				antRunner = new AntRunner(appName, buildFilePath);
				antRunner.runAntBuild();

				finalTimeTaken = System.currentTimeMillis() - initialTimeTaken;
				System.out.println("Time Taken : " + finalTimeTaken / 1000 + "s");

				/*
				 * Multi-Lan APK generation for HelloBank
				 */

				// Set the path of the HelloBank folder and change the strings.xml
				source = Paths.get(DESTINATION_PATH + "/" + FOLDER_NAME_HELLOBANKING);
				excel.updateMultiLangWithRawID(source);

				/*
				 * Runs the Ant for HelloBanking application
				 */
				appName = HELLO_BANKING_APP_NAME + HELLO_BANKING_APP_VERSION;
				buildFilePath = DESTINATION_PATH + "/" + FOLDER_NAME_HELLOBANKING;
				antRunner = new AntRunner(appName, buildFilePath);
				antRunner.runAntBuild();

				finalTimeTaken = System.currentTimeMillis() - initialTimeTaken;
				System.out.println("Time Taken : " + finalTimeTaken / 1000 + "s");

				/*
				 * Move the generated APK from bin folder to the MULTI-LANG
				 * builds folder in the DESTINATION_PATH.
				 */

				// Create the Multi_lang builds folder
				folderCreationFile = new File(DESTINATION_PATH + "/" + FOLDER_MULTI_LANG_BUILDS);
				folderCreationFile.mkdirs();

				apkPath = Paths.get(DESTINATION_PATH + "/" + FOLDER_NAME_EASYBANKING + "/bin" + "/" + EASY_BANKING_APP_NAME
						+ EASY_BANKING_APP_VERSION + ".apk");
				newApkPath = Paths.get(DESTINATION_PATH + "/" + FOLDER_MULTI_LANG_BUILDS + "/" + EASY_BANKING_APP_NAME + EASY_BANKING_APP_VERSION
						+ ".apk");

				System.out.println("EasyBanking APK location : " + apkPath);
				System.out.println("EasyBanking NEW APK location : " + newApkPath);

				Files.move(apkPath, newApkPath, StandardCopyOption.REPLACE_EXISTING);

				apkPath = Paths.get(DESTINATION_PATH + "/" + FOLDER_NAME_HELLOBANKING + "/bin" + "/" + HELLO_BANKING_APP_NAME
						+ HELLO_BANKING_APP_VERSION + ".apk");
				newApkPath = Paths.get(DESTINATION_PATH + "/" + FOLDER_MULTI_LANG_BUILDS + "/" + HELLO_BANKING_APP_NAME + HELLO_BANKING_APP_VERSION
						+ ".apk");

				System.out.println("HelloBanking APK location : " + apkPath);
				System.out.println("HelloBanking NEW APK location : " + newApkPath);

				Files.move(apkPath, newApkPath, StandardCopyOption.REPLACE_EXISTING);

				finalTimeTaken = System.currentTimeMillis() - initialTimeTaken;
				System.out.println("Time Taken : " + finalTimeTaken / 1000 + "s");
			}

		} catch (IOException e) {
			System.out.println("Exception in PackageCopier");
			e.printStackTrace();
		}
	}
}
