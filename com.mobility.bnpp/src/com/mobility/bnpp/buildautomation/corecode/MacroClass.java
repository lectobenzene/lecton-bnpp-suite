package com.mobility.bnpp.buildautomation.corecode;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to handle the IF-THEN-ELSE block replacement using Regular Expressions
 * @author Saravana
 *
 */
public class MacroClass {
	
	Pattern pattern = null;

	/**
	 * Constructor for the class. The Regex pattern is compiled within the constructor
	 * so that it is only compiled once.
	 */
	public MacroClass(){

		/*
		 * The string to handle the Nested Construct in Regex condition - " {{{{{}}}}} "
		 * Since there is no DEPTH in Java to handle this, it is done explicitly here
		 * 
		 * This regex can handle currently 55 nested construct of {}
		 * To increase this number further, copy paste the appropriate expression
		 */
		String NESTED_BLOCK = "[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				+ "(?:\\{[^\\{\\}]*"
				
				// The place where you should do the copy paste to increase the DEPTH
				
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*"
				+ "\\}[^\\{\\}]*)*";
		
		/*
		 * The string that handles the content inside the IF parenthesis
		 */
		String KEY = "(?:"
				+ "[\\(]*"						// To [NON]capture the "(" signs that may appear before "!" sign
				+ "([^\\(\\)isZenaApp&|]*)"     // To capture the "!" sign
				+ "[\\(]*"						// To [NON]capture the "(" signs that may appear before "!" sign
				+ ")"
				+ "MR1Utils\\.isZenaApp\\((?:[^&|]*?)\\)";
		
		String WHITE_SPACE = "(?:\\s)*";
		
		String regex = "(?<!"  	// Looks behind the IF
				+ "(?:"
				+ "/\\*"		// Checks if "/*" is present
				+ "|"
				+ "//"			// Checks if "//" is present
				+ "|"
				+ "else"		// Checks if "else" is present
				+ ")"
				+ "(?:\\s){0,10}"            // same as WHITE_SPACE, but explicit boundary is set since it is in LOOK Behind
				+ ")"
				+ "if"
				+ WHITE_SPACE
				+ "\\("+"("+KEY+")"+"\\)"
				+ WHITE_SPACE
				+ "\\{"
				+ "("
				+ NESTED_BLOCK
				+ ")"
				+ "\\}"
				+ WHITE_SPACE
				+ "(?:"
				+ "else"
				+ WHITE_SPACE
				+ "\\{"
				+ "("
				+ NESTED_BLOCK
				+ ")"
				+ "\\}"
				+ ")?";
		pattern = Pattern.compile(regex, Pattern.MULTILINE|Pattern.DOTALL);
		
	}
	
	/**
	 * Method to convert a file into a StringBuilder object.
	 * UTF-8 encoding is used, since ASCII does not suit the need.
	 * @param path The file name
	 * @return stringBuilder The StringBuilder containing the entire content of the file
	 */
	public static StringBuilder readFileStream(String path){
		StringBuilder stringBuilder  = new StringBuilder();
		
		File sourceFile = new File(path);
		BufferedReader reader = null;
		
		try {
			InputStreamReader fileReader = new InputStreamReader(new FileInputStream(sourceFile), "UTF-8");
			reader = new BufferedReader(fileReader);
			
			while(reader.ready()){
				stringBuilder.append(reader.readLine()+"\n");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("MacroClass : FileNotFoundException");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("MacroClass : IOException");
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("MacroClass : Problem in closing the BufferReader");
			}
		}
		return stringBuilder;
	} 
		

	/**
	 * Splits a single StringBuilder into two StringBuilder, one containing the IF block and the other
	 * containing the ELSE block.
	 * @param stringBuilder The StringBuilder that has the content of the file
	 * @param file To indicate which file has undergone the replacement(for debug purpose only)
	 * @return stringBuilders Returns an array containing both the EasyBanking replacement and the
	 * HelloBanking replacement
	 */
	public StringBuilder[] captureGroup(StringBuilder stringBuilder, Path file) {
		
		StringBuilder[] stringBuilders = new StringBuilder[2];
		stringBuilders[0] = stringBuilder;
		stringBuilders[1] = stringBuilder;
		
		// used for debug purpose to check which files have undergone code splitting.
		// boolean hasFileModified = false;
		
		Matcher matcher = pattern.matcher(stringBuilder);
		while(matcher.find()){
			//hasFileModified = true;
			//count++;
			String allCapture = matcher.group();
			//String ifCapture = matcher.group(1);
			String ifCondition = matcher.group(2);
			String zenaCondition = null;
			String easyCondition = null;
			
			if(ifCondition.equals("!")){
				easyCondition = matcher.group(3);
				zenaCondition = matcher.group(4);
			}else{
				zenaCondition = matcher.group(3);
				easyCondition = matcher.group(4);
			}
			
			/*System.out.println("ALL CAPTURE ------------------------ ");
			System.out.println(allCapture);
			System.out.println("------------------------ IF CAPTURE ------------------------ ");
			System.out.println(ifCapture);
			System.out.println("------------------------ IF CONDITION ------------------------ ");
			System.out.println(ifCondition);
			System.out.println("------------------------ Zena CAPTURE ------------------------ ");
			System.out.println(zenaCondition);
			System.out.println("------------------------ Easy CAPTURE ------------------------ ");
			System.out.println(easyCondition);*/
			
			if(easyCondition != null){
				stringBuilders[0] = new StringBuilder(stringBuilders[0].toString().replace(allCapture, easyCondition));
				//System.out.println(stringBuilders[0]);
				
			}else{
				//System.out.println("EASY condition is NULL");
				stringBuilders[0] = new StringBuilder(stringBuilders[0].toString().replace(allCapture, ""));
			}
			if(zenaCondition != null){
				stringBuilders[1] = new StringBuilder(stringBuilders[1].toString().replace(allCapture, zenaCondition));
				//System.out.println(stringBuilders[1]);
			}else{
				//System.out.println("ZENA condition is NULL");
				stringBuilders[1] = new StringBuilder(stringBuilders[1].toString().replace(allCapture, ""));
			}
		}
		/*if(hasFileModified){
			System.out.println("FILE MODIFIED IN MACRO : "+file.toString());
		}*/
		/*System.out.println("----------------------------------END IS NEAR--------------------------------------");
		System.out.println("----------------------------------EASY BANKING--------------------------------------");
		System.out.println(stringBuilders[0]);
		System.out.println("----------------------------------HELLO BANKING--------------------------------------");
		System.out.println(stringBuilders[1]);*/
		return stringBuilders;
	}

	
	/**
	 * Replaces one String with another String throughout the StringBuilder.
	 * @param stringBuilder The input StringBuilder that contains the content of the file
	 * @param key The String that has to be searched throughout the document
	 * @param replacement The String that is replaced instead of the key
	 * @return The StringBuilder where key is replaced with the replacement string
	 */
	public static StringBuilder replaceAllInstancesOfKey(StringBuilder stringBuilder, String key, String replacement) {
		Pattern pattern = Pattern.compile(key, Pattern.MULTILINE);
		StringBuilder replacedString = new StringBuilder();
		Matcher matcher = pattern.matcher(stringBuilder);
		boolean replacementDone = false;
		
		while(matcher.find()){
			//count++;
			replacedString.append(matcher.replaceAll(replacement));
			replacementDone = true;
		}
		// If no replacement is done, return the original string.
		if(!replacementDone){
			return stringBuilder;
		}
		return replacedString;
	}
	
	/**
	 * Converts the StringBuilder to an InputStream type (encoding "UTF-8").
	 * @param stringBuilder The input StringBuilder
	 * @return The InputStream type of the StringBuilder
	 */
	public static InputStream stringToStream(StringBuilder stringBuilder){
		byte[] bytes = null;
		try {
			bytes = stringBuilder.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("UTF-8 not supported");
		}
		
		InputStream stream = new ByteArrayInputStream(bytes);
		return stream;
	}

	/**
	 * Method to perform the traditional build. The method replaces com.bnpp.easybanking 
	 * into com.bnpp.hellobank for PROJECT_HELLOBANK.
	 * 
	 * @param file The path of the file
	 * @return The StringBuilder object where com.bnpp.easybanking is replaced with com.bnpp.hellobank 
	 */
	public static StringBuilder traditionalBuildReplacement(Path file, int projectType){
		String filePath = file.toString();
		StringBuilder stringBuilder = MacroClass.readFileStream(filePath);
		
		if(projectType == PackageCopier.PROJECT_HELLOBANKING){
			String regexStringToFind = "com\\.bnpp\\.easybanking";
			String stringToReplace = "com.bnpp.hellobank";
			stringBuilder = MacroClass.replaceAllInstancesOfKey(stringBuilder, regexStringToFind, stringToReplace);
		}
		
		return stringBuilder;
	}
	
	/**
	 * Returns the current date as a String in any particular format
	 * 
	 * @param dateFormat The DateFormat string. eg: MMMdd_HH_mm
	 * @return The current date in the required format as a String
	 */
	public static String getDateFromStringFormat(String dateFormat){
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		
		try{
			simpleDateFormat.applyPattern(dateFormat);
		}catch(Exception e){
			return "invalid_pattern";
		}
		
		return simpleDateFormat.format(date);
	}
	
	
	/**
	 * Method to convert string into date/time characters based on the characters
	 * between the %% %% string.
	 * 
	 * @param fileName The string that has the %%aaaaaa%% in it.
	 * @return the string where contents between the %% %% is replaced
	 * with the date format of the string.
	 */
	public static String convertStringWithDate(String fileName){
		
		Pattern fileWithDatePattern = Pattern.compile("(?:%%)([^%]*)(?:%%)", Pattern.DOTALL);
		Matcher matcher = fileWithDatePattern.matcher(fileName);
		
		while(matcher.find()){
			fileName = fileName.replace(matcher.group(0), getDateFromStringFormat(matcher.group(1)));
		}
		
		return fileName;
		
	}
	
	
	public static String[] getBuildTypesFromConfigFile(String destinationPath){
		String configFileContent = readFileStream(destinationPath).toString();
		
		Pattern pattern = Pattern.compile("Types : (.*)");
		Matcher matcher = pattern.matcher(configFileContent);
		
		String typeOfBuildString = null;
		
		while(matcher.find()){
			typeOfBuildString = matcher.group(1);
		}
		
		String[] typesOfBuild = typeOfBuildString.split(" , ");
		return typesOfBuild;
	}
}
