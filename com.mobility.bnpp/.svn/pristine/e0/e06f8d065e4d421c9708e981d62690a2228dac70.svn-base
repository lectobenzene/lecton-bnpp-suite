package com.mobility.bnpp.buildautomation.corecode;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Class used for splitting the RES folder into EasyBanking RES and HelloBanking
 * RES.
 * 
 * @author Saravana
 * 
 */
public class SplitResourceFilter extends SimpleFileVisitor<Path> {
	Path source = null;
	Path target = null;

	int projectType = 0;

	public SplitResourceFilter(Path source, Path target, int projectType) {
		this.source = source;
		this.target = target;
		this.projectType = projectType;

		// target is changed based on the type of project
		setTarget(target, projectType);

	}

	/**
	 * Method to change the target path based on the type of project
	 * 
	 * @param target
	 *            The input target set in the constructor
	 * @param projectType
	 *            The type of project
	 */
	private void setTarget(Path target, int projectType) {
		switch (projectType) {
		case PackageCopier.PROJECT_EASYBANKING:
			this.target = target.resolve(PackageCopier.FOLDER_NAME_EASYBANKING).resolve("res");
			break;
		case PackageCopier.PROJECT_HELLOBANKING:
			this.target = target.resolve(PackageCopier.FOLDER_NAME_HELLOBANKING).resolve("res");
			break;
		case PackageCopier.PROJECT_FINTRO:
			this.target = target.resolve(PackageCopier.FOLDER_NAME_FINTRO).resolve("res");
			break;
		default:
			break;
		}
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		Path newDir = target.resolve(source.relativize(dir));
		// System.out.println("Path Dir : " + dir);

		// Certain files are skipped from being copied
		if (Files.isHidden(dir)) {
			return FileVisitResult.SKIP_SUBTREE;
		}
		
		// Remove the values-XX-car folders if the BuildType is not QA
		if(!PackageCopier.BUILD_TYPE.equals("QA")){
			if(dir.endsWith("values-en-car") || dir.endsWith("values-fr-car") || dir.endsWith("values-de-car") || dir.endsWith("values-nl-car")){
				return FileVisitResult.SKIP_SUBTREE;
			}
		}
		
		Files.copy(dir, newDir, StandardCopyOption.REPLACE_EXISTING);

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

		Path newFile = target.resolve(source.relativize(file));

		String filePath = file.toString();
		StringBuilder stringBuilder = MacroClass.readFileStream(filePath);

		if (projectType == PackageCopier.PROJECT_EASYBANKING) {
			if (file.endsWith("values/strings.xml")) {

				// AppName is changed
				String regexStringToFind = "<string name=\"app_name\">[^<]*</string>";
				String stringToReplace = "<string name=\"app_name\">" + PackageCopier.EASY_BANKING_APP_NAME + PackageCopier.EASY_BANKING_APP_VERSION
						+ "</string>";
				stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
				//System.out.println("Easy String changed");
				InputStream in = MacroClass.stringToStream(stringBuilder);
				Files.copy(in, newFile, StandardCopyOption.REPLACE_EXISTING);
			} else if (file.endsWith("values/config.xml")) {

				//PE appVersion is changed
				String regexStringToFind = "<string name=\"app_version\">[^<]*</string>";
				String stringToReplace = null;
				if (PackageCopier.EASY_BANKING_PE_APP_VERSION != null) {
					stringToReplace = "<string name=\"app_version\">" + PackageCopier.EASY_BANKING_PE_APP_VERSION + "</string>";
				} else {

					stringToReplace = "<string name=\"app_version\">" + PackageCopier.getPEAppVersion(PackageCopier.EASY_BANKING_APP_VERSION)
							+ "</string>";
				}
				stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
				//System.out.println("Easy config changed");
				InputStream in = MacroClass.stringToStream(stringBuilder);
				Files.copy(in, newFile, StandardCopyOption.REPLACE_EXISTING);
			} else {
				Files.copy(file, newFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} else {
			if (file.endsWith("values/strings.xml")) {

				// AppName is changed
				String regexStringToFind = "<string name=\"app_name\">[^<]*</string>";
				String stringToReplace = "<string name=\"app_name\">" + PackageCopier.HELLO_BANKING_APP_NAME
						+ PackageCopier.HELLO_BANKING_APP_VERSION + "</string>";
				stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
				//System.out.println("Hello String changed");

				InputStream in = MacroClass.stringToStream(stringBuilder);
				Files.copy(in, newFile, StandardCopyOption.REPLACE_EXISTING);
			} else if (file.endsWith("values/config.xml")) {

				//PE appVersion is changed
				String regexStringToFind = "<string name=\"app_version\">[^<]*</string>";
				String stringToReplace = null;
				if (PackageCopier.HELLO_BANKING_PE_APP_VERSION != null) {
					stringToReplace = "<string name=\"app_version\">" + PackageCopier.HELLO_BANKING_PE_APP_VERSION + "</string>";
				} else {
					stringToReplace = "<string name=\"app_version\">" + PackageCopier.getPEAppVersion(PackageCopier.HELLO_BANKING_APP_VERSION)
							+ "</string>";
				}
				stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
				//System.out.println("Hello config changed");

				InputStream in = MacroClass.stringToStream(stringBuilder);
				Files.copy(in, newFile, StandardCopyOption.REPLACE_EXISTING);
			} else if (file.getFileName().toString().endsWith(".xml")) {

				if(PackageCopier.IS_TRADITIONAL_METHOD_ENABLED){
					stringBuilder = MacroClass.traditionalBuildReplacement(file, projectType);
				}else{
					// Namespace replaced from EasyBanking to HelloBanking
					String regexStringToFind = "xmlns:foo=\"http://schemas\\.android\\.com/apk/res/com\\.bnpp\\.easybanking\"";
					String stringToReplace = "xmlns:foo=\"http://schemas.android.com/apk/res/com.bnpp.hellobank\"";
					stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
				}

				InputStream in = MacroClass.stringToStream(stringBuilder);
				Files.copy(in, newFile, StandardCopyOption.REPLACE_EXISTING);
			} else {
				Files.copy(file, newFile, StandardCopyOption.REPLACE_EXISTING);
			}
		}

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

}
