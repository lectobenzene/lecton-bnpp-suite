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
 * Class used for splitting the SRC folder into EasyBanking SRC and HelloBanking
 * SRC.
 * 
 * @author Saravana
 * 
 */
public class SplitSourceFilter extends SimpleFileVisitor<Path> {

	Path source = null;
	Path target = null;

	Path easyBankingPath = null;
	Path helloBankingPath = null;

	
	StringBuilder[] stringBuilders = new StringBuilder[2];
	
	public SplitSourceFilter(Path source, Path target) {
		this.source = source;
		this.target = target;

		this.easyBankingPath = target.resolve(PackageCopier.FOLDER_NAME_EASYBANKING).resolve("src");
		this.helloBankingPath = target.resolve(PackageCopier.FOLDER_NAME_HELLOBANKING).resolve("src");
		
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		Path newEasyBankingPath = easyBankingPath.resolve(source.relativize(dir));
		Path newHelloBankingPath = helloBankingPath.resolve(source.relativize(dir));

		//System.out.println("Path Dir : "+dir);
		//System.out.println("easyBankingPath : "+newEasyBankingPath);
		//System.out.println("helloBankingPayth : "+newHelloBankingPath);

		if (Files.isHidden(dir)) {
			return FileVisitResult.SKIP_SUBTREE;
		}
		Files.copy(dir, newEasyBankingPath, StandardCopyOption.REPLACE_EXISTING);
		Files.copy(dir, newHelloBankingPath, StandardCopyOption.REPLACE_EXISTING);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		Path newEasyBankingPath = easyBankingPath.resolve(source.relativize(file));
		Path newHelloBankingPath = helloBankingPath.resolve(source.relativize(file));

		if (file.getFileName().toString().endsWith(".java")) {

			String filePath = file.toString();
			StringBuilder stringBuilder = MacroClass.readFileStream(filePath);
			/*
			 * Code added to remove "SuppressLint" from the code. Ant build
			 * fails when it encounters this SuppressLint. It can appear in two
			 * ways.
			 * 
			 * One as an IMPORT statement like
			 * "import android.annotation.SuppressLint;" The other is as an
			 * ANNOTATION like "@SuppressLint("SimpleDateFormat")"
			 * 
			 * Only if the java file contains the import statement, it will
			 * contain the annotation, hence, the Import statement is checked
			 * and removed. Thus if the length of the stringBuilder before and
			 * after is different, then it means that IMPORT statement was
			 * found. Then the annotation is cheked and replaced.
			 */
			long initialStringBuilderLength = stringBuilder.length();

			String regexStringToFind = "import android\\.annotation\\.SuppressLint;";
			String stringToReplace = "";
			stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);

			if (initialStringBuilderLength != stringBuilder.length()) {
				regexStringToFind = "@SuppressLint\\([^\\)]*\\)";
				stringToReplace = "";
				stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
			}
			if(PackageCopier.IS_CODE_SPLIT_ENABLED){
				
				MacroClass macro = new MacroClass();
				
				/*
				 * IF ELSE replacement is done here. file is sent as a parameter,
				 * for debug purpose only.
				 */
				stringBuilders = macro.captureGroup(stringBuilder, file);
			}else{
				stringBuilders[0] = stringBuilder;
				/*
				 *  stringBuilders[1] is the same as stringBuilder. This method is used to create two
				 *  StringBuilder objects rather than two variables pointing to the same.
				 */
				
				stringBuilders[1] = stringBuilder.append("");
			}

			InputStream in = MacroClass.stringToStream(stringBuilders[0]);
			Files.copy(in, newEasyBankingPath, StandardCopyOption.REPLACE_EXISTING);
			//System.out.println(file.getFileName());
			
			if (PackageCopier.IS_TRADITIONAL_METHOD_ENABLED) {
				regexStringToFind = "com\\.bnpp\\.easybanking";
				stringToReplace = "com.bnpp.hellobank";
				stringBuilders[1] = MacroClass.replaceAllInstancesOfKey(stringBuilders[1], regexStringToFind, stringToReplace);
				 
			} else{
				// EasyBanking import statements are changed into HelloBanking import statements		
				stringBuilders[1] = splitStringBuilder(stringBuilders[1]);
			}


			// Specific case : Import statement is added for HelloBanking app in EasyBankingApp.java file
			if (file.endsWith("EasyBankingApp.java")) {
				regexStringToFind = "package com\\.bnpp\\.easybanking;";
				stringToReplace = "package com.bnpp.easybanking;  import com.bnpp.hellobank.R;";
				stringBuilders[1] = MacroClass.replaceAllInstancesOfKey(stringBuilders[1], regexStringToFind, stringToReplace);
			}

			in = MacroClass.stringToStream(stringBuilders[1]);
			Files.copy(in, newHelloBankingPath, StandardCopyOption.REPLACE_EXISTING);
		}

		return FileVisitResult.CONTINUE;
	}

	/**
	 * Method to change the EasyBanking import statements into HelloBanking
	 * import statements.
	 * 
	 * @param stringBuilder
	 *            The StringBuilder that contains the content of the file
	 * @return The modified StringBuilder where the replacement is done
	 */
	private StringBuilder splitStringBuilder(StringBuilder stringBuilder) {
		String regexStringToFind = "import com\\.bnpp\\.easybanking\\.R";
		String stringToReplace = "import com.bnpp.hellobank.R";
		StringBuilder zenaStringBuilder = new StringBuilder();
		zenaStringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
		return zenaStringBuilder;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

}
