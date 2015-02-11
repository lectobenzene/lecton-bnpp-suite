package com.mobility.bnpp.buildautomation.corecode;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Class to copy the entire source folder (with the exception of few) 
 * into EasyBanking and HelloBanking folders.
 * 
 * @author Saravana
 *
 */
public class CopyFileFilter extends SimpleFileVisitor<Path> {

	Path source = null;
	Path target = null;
	int projectType = 0;

	/*
	 * Variable that is used to make sure that a bunch of code in the
	 * preVisitDirectory method is executed only once.
	 * Better way is available, but for time being this way is chosen.
	 */
	int i = 0;
	ArrayList<Path> dirPathsNotNeededArrayList;

	/**
	 * Constructor for the class.
	 * @param source The Path of the source folder
	 * @param target The path of the target folder
	 * @param projectType Denotes the type of project
	 */
	public CopyFileFilter(Path source, Path target, int projectType) {
		this.source = source;
		this.projectType = projectType;
		
		// Target is changed based on the projectType
		setTarget(target, projectType);
	}

	/**
	 * Method to change the target path based on the type of project
	 * @param target The input target set in the constructor
	 * @param projectType The type of project
	 */
	private void setTarget(Path target, int projectType) {
		switch (projectType) {
		case PackageCopier.PROJECT_EASYBANKING:
			this.target = target.resolve(PackageCopier.FOLDER_NAME_EASYBANKING);
			break;
		case PackageCopier.PROJECT_HELLOBANKING:
			this.target = target.resolve(PackageCopier.FOLDER_NAME_HELLOBANKING);
			break;
		case PackageCopier.PROJECT_FINTRO:
			this.target = target.resolve(PackageCopier.FOLDER_NAME_FINTRO);
			break;
		default:
			break;
		}
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		Path newFile = target.resolve(source.relativize(file));

		// Files that is omitted from the copy
		Path[] pathsNotNeeded = new Path[] { Paths.get("read_me.txt") };
		List<Path> pathsNotNeededArrayList = Arrays.asList(pathsNotNeeded);

		// Certain files are skipped from being copied
		if (Files.isHidden(file) || (pathsNotNeededArrayList.contains(file.getFileName()) && file.getParent().equals(source))) {
			return FileVisitResult.CONTINUE;
		}


		if (file.endsWith("AndroidManifest.xml")) {
			/*
			 *  Alters the AndroidManifest file for EasyBanking and HelloBanking separately
			 */
			StringBuilder stringBuilder;
			/*
			 *  AndroidManifest is changed to meet certain criteria.
			 *  This can be removed if the changes are made in the 
			 *  AndroidManifest and committed in the SVN.
			 */
			if(PackageCopier.IS_TRADITIONAL_METHOD_ENABLED){
				stringBuilder = MacroClass.traditionalBuildReplacement(file, projectType);
			}else{
				stringBuilder = preChangeManifestXML(file);
			}
			
			InputStream in = null;
			if (projectType == PackageCopier.PROJECT_EASYBANKING) {
				// The versionName is changed in the file
				String regexStringToFind = "android:versionName=\"([^\"]+)\"";
				String stringToReplace = "android:versionName=\"" + PackageCopier.EASY_BANKING_APP_VERSION + "\"";
				stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);

			} else if (projectType == PackageCopier.PROJECT_HELLOBANKING) {
				// The package name is changed in the file
				String regexStringToFind = "package=\"com\\.bnpp\\.easybanking\"";
				String stringToReplace = "package=\"com.bnpp.hellobank\"";
				stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);

				// The versionName is changed in the file
				regexStringToFind = "android:versionName=\"([^\"]+)\"";
				stringToReplace = "android:versionName=\"" + PackageCopier.HELLO_BANKING_APP_VERSION + "\"";
				stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
				
				// The app upgrade receiver's package name is changed in the file
				regexStringToFind = "android:path=\"com\\.bnpp\\.easybanking\"";
				stringToReplace = "android:path=\"com.bnpp.hellobank\"";
				stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
			}
			in = MacroClass.stringToStream(stringBuilder);
			Files.copy(in, newFile, StandardCopyOption.REPLACE_EXISTING);

		} else if (file.endsWith("comScore.properties")) {
			/*
			 *  Alters the config.xml file for EasyBanking and HelloBanking separately
			 */
			String filePath = file.toString();
			StringBuilder stringBuilder = MacroClass.readFileStream(filePath);
			InputStream in = null;
			if (projectType == PackageCopier.PROJECT_EASYBANKING) {
				/*
				 * The ZENA signature is replaced with EASY_BANKING signature.
				 * The source config.xml file has the EASY_BANKING signature by default and
				 * hence there is no need for this code. But as a backup, incase if the source
				 * config.xml has a ZENA signature, it is replaced with EASY_BANKING signature.
				 */
				String regexStringToFind = "PixelURL=http://nl\\.sitestat\\.com/fortis/zena/s\\?zenaandroid";
				String stringToReplace = "PixelURL=http://nl.sitestat.com/fortis/app-bnpparibasfortis-android/s?pagename";
				stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
				
				regexStringToFind = "AppName=zenaandroid";
				stringToReplace = "AppName=fortis";
				stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
				
				
			} else if (projectType == PackageCopier.PROJECT_HELLOBANKING) {
				
				// The EASY_BANKING	signature is replaced with ZENA signature
				String regexStringToFind = "PixelURL=http://nl\\.sitestat\\.com/fortis/app-bnpparibasfortis-android/s\\?pagename";
				String stringToReplace = "PixelURL=http://nl.sitestat.com/fortis/zena/s?zenaandroid";
				stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
				
				regexStringToFind = "AppName=fortis";
				stringToReplace = "AppName=zenaandroid";
				stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
			}
			in = MacroClass.stringToStream(stringBuilder);
			Files.copy(in, newFile, StandardCopyOption.REPLACE_EXISTING);

		} else if (file.endsWith(PackageCopier.BUILD_TYPE_CONFIG_FILE)) {
			/*
			 *  Alters the config.txt file in the assets folder.
			 */
			String filePath = file.toString();
			StringBuilder stringBuilder = MacroClass.readFileStream(filePath);
			InputStream in = null;
			
			String regexStringToFind = "Build-Type = (.*)";
			String stringToReplace = "Build-Type = "+PackageCopier.BUILD_TYPE;
			stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
			
			in = MacroClass.stringToStream(stringBuilder);
			Files.copy(in, newFile, StandardCopyOption.REPLACE_EXISTING);

		} else if (file.endsWith("config.xml")) {
			/*
			 *  Alters the config.xml file for EasyBanking and HelloBanking separately
			 */
			String filePath = file.toString();
			StringBuilder stringBuilder = MacroClass.readFileStream(filePath);
			InputStream in = null;
			if (projectType == PackageCopier.PROJECT_EASYBANKING) {
				/*
				 * The ZENA signature is replaced with EASY_BANKING signature.
				 * The source config.xml file has the EASY_BANKING signature by default and
				 * hence there is no need for this code. But as a backup, incase if the source
				 * config.xml has a ZENA signature, it is replaced with EASY_BANKING signature.
				 */
				String regexStringToFind = "keystore_name=\"BNPP_ZENA\\.keystore\" storepass=\"bnpp@zenabanking\" keypass=\"bnpp@zenabanking\" keystore_alias=\"zena\"";
				String stringToReplace = "keystore_name=\"BNPP_Easy_Banking.keystore\" storepass=\"bnpp@easybanking\" keypass=\"bnpp@easybanking\" keystore_alias=\"bnpp\"";
				stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
			} else if (projectType == PackageCopier.PROJECT_HELLOBANKING) {
				
				// The EASY_BANKING	signature is replaced with ZENA signature
				String regexStringToFind = "keystore_name=\"BNPP_Easy_Banking\\.keystore\" storepass=\"bnpp@easybanking\" keypass=\"bnpp@easybanking\" keystore_alias=\"bnpp\"";
				String stringToReplace = "keystore_name=\"BNPP_ZENA.keystore\" storepass=\"bnpp@zenabanking\" keypass=\"bnpp@zenabanking\" keystore_alias=\"zena\"";
				stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
			}
			in = MacroClass.stringToStream(stringBuilder);
			Files.copy(in, newFile, StandardCopyOption.REPLACE_EXISTING);

		} else if (file.endsWith("build.xml")) {
			String filePath = file.toString();
			StringBuilder stringBuilder = MacroClass.readFileStream(filePath);
			InputStream in = null;
			
			String regexStringToFind = "<javac ";
			String stringToReplace = "<javac source=\"1.6\" fork=\"yes\" ";
			stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
			
			regexStringToFind = "encoding=\"ascii\"";
			stringToReplace = "encoding=\"utf-8\"";
			stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
			
			in = MacroClass.stringToStream(stringBuilder);
			Files.copy(in, newFile, StandardCopyOption.REPLACE_EXISTING);
		} else {
			Files.copy(file, newFile, StandardCopyOption.REPLACE_EXISTING);
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		Path destDir = target.resolve(source.relativize(dir));

		while (i == 0) {
			i++;
			
			// folders to skip from being copied.
			Path[] dirPathsNotNeeded = new Path[] { Paths.get("bin"), Paths.get("pkchr"), Paths.get("gen"), Paths.get("src"),
					Paths.get("src_generator"), Paths.get("res"), Paths.get("res_hellobank"), Paths.get("res_fintro") };
			dirPathsNotNeededArrayList = new ArrayList<Path>();
			for (Path path : dirPathsNotNeeded) {
				dirPathsNotNeededArrayList.add(path);
			}
		}
		
		// Certain folders are skipped from being copied.
		if (Files.isHidden(dir) || (dirPathsNotNeededArrayList.contains(dir.getFileName()) && dir.getParent().equals(source))) {
			return FileVisitResult.SKIP_SUBTREE;
		}

		Files.copy(dir, destDir, StandardCopyOption.REPLACE_EXISTING);

		return FileVisitResult.CONTINUE;

	}

	/**
	 * Method that changes the AndroidManifest file to conform to 
	 * a criteria. This method can be removed once the changes mentioned below
	 * are made in the AndroidManifest file itself and committed in the SVN.
	 * This method is temporarily used until that SVN commit is made.
	 * 
	 * @param file The file pointing to the AndroidManifest.xml
	 * @return The StringBuilder containing the modified content of the file
	 */
	public StringBuilder preChangeManifestXML(Path file) {
		String filePath = file.toString();
		StringBuilder stringBuilder = MacroClass.readFileStream(filePath);
		String regexStringToFind = "android:name=\"\\.";
		String stringToReplace = "android:name=\"com.bnpp.easybanking.";
		stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
		return stringBuilder;
	}

}
