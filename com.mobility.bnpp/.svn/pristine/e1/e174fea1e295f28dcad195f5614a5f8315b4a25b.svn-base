package com.mobility.bnpp.integratemultilang;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.mobility.bnpp.buildautomation.corecode.PackageCopier;

/**
 * Class to update "strings.xml" [multi-lang] files from CRUDE documents
 * provided in the knowmax.
 * 
 * @author 587418 / Saravana T.
 */
public class ExcelParser {

	final static MessageConsole myConsole = findConsole("BNPP");
	final static MessageConsoleStream out = myConsole.newMessageStream();
	/*
	 * Location of the Source Excel documents.
	 * 
	 * CHANGE THE FOLLOWING STRINGS TO MATCH WITH THE SOURCE FILE.
	 */
	public static String SOURCE_FILE_NAME_EASY_BANKING = "/Users/Saravana/Documents/BNPP/Documentation/Multi-Lang/Oct01/ZENA_merged_20130927.xls";
	public static String SOURCE_FILE_NAME_HELLO_BANKING = "/Users/Saravana/Documents/BNPP/Documentation/Multi-Lang/Aug30/ZENA_merged_20130830.xls";

	/*
	 * Location of the Project Directory.
	 * 
	 * CHANGE THE FOLLOWING STRINGS TO MATCH WITH THE CURRENT PROJECT STRUCTURE.
	 */

	static String PROJECT_DIR = "/Users/Saravana/Documents/Workspace/MR3_Aug28_Wave1/";
	final static String EASYBANKING_RESOURCE_DIR = "/res/";
	final static String HELLOBANKING_RESOURCE_DIR = "/res_hellobank/";

	public static int COLUMN_NUMBER_ID;
	public static int COLUMN_NUMBER_EN;
	public static int COLUMN_NUMBER_NL;
	public static int COLUMN_NUMBER_FR;
	public static int COLUMN_NUMBER_DE;

	public static final String COLUMN_NAME_ID = "TEXT ID";
	public static final String COLUMN_NAME_EN = "EN";
	public static final String COLUMN_NAME_NL = "NLB";
	public static final String COLUMN_NAME_FR = "FR";
	public static final String COLUMN_NAME_DE = "DE";

	// Values that has to have an underline tag and hence handled in a different way.
	String[] underlinedArray = { "42631", "42632", "47823" };
	List<String> underlinedArrayList = Arrays.asList(underlinedArray);

	// String value that should have a # instead of a SPACE.
	String hashContent = "48021";

	// Values that has to be ommited.
	String[] omittableArray = { "TEXT ID " };
	List<String> omittableArrayList = Arrays.asList(omittableArray);

	// Strings used for Excel Sheet parsing.
	String start = "<string name=";
	String end_withCData = "]]></string>";
	String end = "</string>";

	// Strings used for detecting contents to be replaced in the destination file.
	static final String startString = "<!--START.START.START.START.START-->";
	static final String endString = "<!--END.END.END.END.END-->";
	static final String shortStartString = "START.START";
	static final String shortEndString = "END.END";

	/*
	 * StringBuilder is used as an alternative to String as to reduce the
	 * creation of lot of objects in the FOR loop.
	 */
	StringBuilder nonUnderlinedContent = new StringBuilder();
	StringBuilder underlinedContent = new StringBuilder();
	StringBuilder cellContent = new StringBuilder();

	// Used for writing the content to file.
	RandomAccessFile accessFile = null;

	/**
	 * Used for convenience, on the Language perspective.
	 * 
	 * @author Saravana
	 * 
	 */
	public static enum Language {
		EN("values-en/", COLUMN_NAME_EN, COLUMN_NUMBER_EN), NL("values-nl/", COLUMN_NAME_NL, COLUMN_NUMBER_NL), FR("values-fr/", COLUMN_NAME_FR,
				COLUMN_NUMBER_FR), DE("values-de/", COLUMN_NAME_DE, COLUMN_NUMBER_DE);

		private String name;
		private String columnName;
		/**
		 * Used for Reference to the excel sheet.
		 */
		private int columnNumber;

		Language(String name, String columnName, int columnNumber) {
			this.name = name;
			this.columnNumber = columnNumber;
			this.columnName = columnName;
		}

		public String getName() {
			return name;
		}

		public String getColumnName() {
			return columnName;
		}

		public int getColumnNumber() {
			return columnNumber;
		}

		public void setColumnNumber(int columnNo) {
			columnNumber = columnNo;
		}
	};

	/**
	 * Used for reading the content from the excel sheet for a specified
	 * language and return the result in s StringBuilder format.
	 * 
	 * @param sourceName
	 *            Source Excel file.
	 * @param lang
	 *            The current language
	 * @return Content to be written to the file.
	 */
	private StringBuilder readExcelSheets(String sourceName, Language lang) {
		StringBuilder totalContentFromExcelSheet = new StringBuilder();
		try {

			// Excel initialization for reading the file.
			WorkbookSettings ws = new WorkbookSettings();
			ws.setEncoding("ISO-8859-1");
			File file = new File(sourceName);
			Workbook wb = null;

			// If the File doesn't exist, throw ERROR.
			if (file.exists()) {
				wb = Workbook.getWorkbook(file, ws);
			} else {
				out.println("ERROR : Source file does not exit!");
				//System.exit(0);
				throw new FileNotFoundException();
			}

			Pattern regex = Pattern.compile("[&<>]");

			for (int sheetNo = 0; sheetNo < wb.getNumberOfSheets(); sheetNo++) {
				Sheet sheet = wb.getSheet(sheetNo);
				int columns = sheet.getColumns();
				int rows = sheet.getRows();

				int rowNo = 0;

				for (int column = 0; column < columns; column++) {
					String cellContent = sheet.getCell(column, rowNo).getContents();
					System.out.println(cellContent);
					
					if (cellContent.equals(lang.getColumnName())) {
						lang.setColumnNumber(column);
					}else if(cellContent.equals(COLUMN_NAME_ID)){
						COLUMN_NUMBER_ID = column;
					}
				}

				for (int row = 0; row < rows; row++) {
					for (int col = 0; col < columns; col++) {

						// Get the "name" field for the strings.
						if (col == COLUMN_NUMBER_ID) {

							// Get the content from the cell, "name" field of the strings.
							cellContent.append(sheet.getCell(col, row).getContents());

							// Checks for the strings were the <u></u> tags should be added.
							if (underlinedArrayList.contains(cellContent.toString())) {
								underlinedContent.append(start).append("\"_" + cellContent);
							} else if (hashContent.equals(cellContent.toString()) && SOURCE_FILE_NAME_HELLO_BANKING.equals(sourceName)) {
								underlinedContent.append("<!--").append(start).append("\"_" + cellContent);
								out.println("Check String ID - " + cellContent.toString());
							} else if (omittableArrayList.contains(cellContent.toString())) {
								// Do nothing.
							} else {
								nonUnderlinedContent.append(start).append("\"_" + cellContent);
							}
						}

						// Gets the "content" of the strings based on the language.
						if (col == lang.getColumnNumber()) {

							String content = sheet.getCell(col, row).getContents();

							Matcher matcher = regex.matcher(content);
							content = content.replace("\'", "\\'");

							if (matcher.find()) {
								((nonUnderlinedContent.append("\"><![CDATA[")).append(content)).append(end_withCData);
							} else {
								if (underlinedArrayList.contains(cellContent.toString())) {
									(underlinedContent.append("\"><u>").append(content)).append("</u>").append(end).append("\n");
								} else if (hashContent.equals(cellContent.toString()) && SOURCE_FILE_NAME_HELLO_BANKING.equals(sourceName)) {
									underlinedContent.append("\">").append(content).append(end).append("-->").append("\n");
									out.println("Content - " + content.toString());

								} else if (omittableArrayList.contains(cellContent.toString())) {
									// Do nothing.
								} else {
									(nonUnderlinedContent.append("\">").append(content)).append(end);
								}
							}

						}
					}
					cellContent.setLength(0);
					nonUnderlinedContent.append("\n");
				}
			}
			totalContentFromExcelSheet.append("\n").append(underlinedContent).append(nonUnderlinedContent);
		} catch (Exception ioe) {
			return null;
		} finally {
			underlinedContent.setLength(0);
			nonUnderlinedContent.setLength(0);
		}
		return totalContentFromExcelSheet;
	}

	/**
	 * Used to update the file based on LANGUAGE and BRAND.
	 * 
	 * @param sourceFile
	 *            The Source excel file.
	 * @param lang
	 *            The specified language.
	 */
	public void updateFile(String sourceFile, Language lang) {
		String destinationFile = null;
		if (SOURCE_FILE_NAME_EASY_BANKING.equals(sourceFile)) {

			switch (lang) {
			case EN:
				// Two files have to be updated for EN.
				destinationFile = PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values/strings.xml";
				updateDestinationFile(destinationFile, sourceFile, lang);
				destinationFile = PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values-en/strings.xml";
				break;
			case DE:
				destinationFile = PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values-de/strings.xml";
				break;
			case FR:
				destinationFile = PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values-fr/strings.xml";
				break;
			case NL:
				destinationFile = PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values-nl/strings.xml";
				break;
			default:
				out.println("ERROR : You are doing something wrong [switch:default]");
				break;
			}
		} else if (SOURCE_FILE_NAME_HELLO_BANKING.equals(sourceFile)) {
			switch (lang) {
			case EN:
				// Two files have to be updated for EN.
				destinationFile = PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values/strings_en.xml";
				updateDestinationFile(destinationFile, sourceFile, lang);
				destinationFile = PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values-en/strings.xml";
				break;
			case DE:
				destinationFile = PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values-de/strings.xml";
				break;
			case FR:
				destinationFile = PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values-fr/strings.xml";
				break;
			case NL:
				destinationFile = PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values-nl/strings.xml";
				break;
			default:
				out.println("ERROR : You are doing something wrong [switch:default]");
				break;
			}
		}

		// Check if the destination file Exists! That is, verify whether the project structure is correct.
		if (destinationFile != null) {
			updateDestinationFile(destinationFile, sourceFile, lang);
		} else {
			out.println("ERROR : DestinationFile name is NULL");
		}
	}

	/**
	 * Used to update the destination file.
	 * 
	 * @param destinationFile
	 *            The file in the project that has to be updated.
	 * @param sourceExcelFile
	 *            The source excel file.
	 * @param lang
	 *            The specified language.
	 */
	public void updateDestinationFile(String destinationFile, String sourceExcelFile, Language lang) {
		int startFilePointer = 0;
		int endFilePointer = 0;
		long endOfFilePointer = 0;

		StringBuilder fileEndingContent = new StringBuilder();

		//System.out.format("REQUEST : Trying to update the destination file %s\n",destinationFile);

		try {
			File file = new File(destinationFile);

			// Check if the file exits, or throw ERROR.
			if (file.exists()) {
				accessFile = new RandomAccessFile(file, "rw");
			} else {
				out.println("ERROR : File does not exist! [Provide proper Folder structure]");
				//System.exit(0);
				throw new IOException();
			}

			startFilePointer = getFilePointerFromString(accessFile, startString, true);
			endFilePointer = getFilePointerFromString(accessFile, endString, false);

			// Reads the content that is below the EndString and stores them to a bufferBuilder.
			accessFile.seek(endFilePointer);
			byte[] remainingData = new byte[(int) accessFile.length() - endFilePointer];
			accessFile.readFully(remainingData);
			fileEndingContent.append(new String(remainingData));

			StringBuilder contentFromExcelSheet = readExcelSheets(sourceExcelFile, lang);
			out.println("Current Col Number" + lang.getColumnNumber());
			
			// If Column Number is 0, it means that there is a problem in the EXCEL SHEET.
			if(0 == lang.getColumnNumber()){
				out.println("------------------- WARNING ---------------------");
				out.println("NO COLUMN EXITS IN EXCEL SHEET FOR "+lang.getColumnName());
				out.println("Check Excel Sheet");
			}
			
			if (contentFromExcelSheet == null) {
				throw new IOException();
			}
			// Starts to write the content fromt the startString.
			accessFile.seek(startFilePointer);

			accessFile.write(contentFromExcelSheet.toString().getBytes("UTF-8"));
			accessFile.write(fileEndingContent.toString().getBytes("UTF-8"));

			// Remove all content that is present after the "</resources>" tag.
			accessFile.seek(startFilePointer);
			endOfFilePointer = getFilePointerFromString(accessFile, "</resources>", true);
			accessFile.setLength(endOfFilePointer);

		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} finally {
			try {
				accessFile.close();
				out.println(destinationFile + " sucessfully updated!");
			} catch (IOException e) {

			}
		}
	}

	/**
	 * 
	 * @param accessFile
	 *            Destination file to be updated.
	 * @param findString
	 *            The comment line in the strings.xml
	 * @param flag
	 *            This determines whether the pointer should point to the start
	 *            of the "findString" or at the end of the "findString". True
	 *            for start of the string and false otherwise.
	 * @return The file pointer.
	 */
	public static int getFilePointerFromString(RandomAccessFile accessFile, String findString, boolean flag) {
		int fileBufferLength = 1000;
		long currentFilePointer = 0;
		int keyPosition = -1;
		byte[] fileContent = null;

		//System.out.format("REQUEST : Trying to find FilePointer for %s\n",findString);

		try {
			long fileLength = accessFile.length();
			while (keyPosition < 0) {
				currentFilePointer = accessFile.getFilePointer();

				fileContent = new byte[fileBufferLength];

				accessFile.read(fileContent, 0, fileBufferLength);
				String tempString = new String(fileContent, "ISO-8859-1");

				// The findString is searched in the tempString and the position is noted 
				keyPosition = tempString.indexOf(findString);

				if (flag) {
					// This condition is written to make sure that the search works even
					// when the StartString got split during the read process.
					if ((ExcelParser.startString).equals(findString)) {
						keyPosition = tempString.indexOf(shortStartString);
					}
				} else {
					// The same case as above.
					if ((ExcelParser.endString).equals(findString)) {
						keyPosition = tempString.indexOf(shortEndString);
					}
				}

				// Check if the COMMMENT lines exists in the Destination file, or throw ERROR.
				if (currentFilePointer >= fileLength) {
					//out.println("ERROR : Comment Lines in the strings.xml has been removed!");
					//System.exit(0);
					throw new IOException();
				}
			}
			currentFilePointer = currentFilePointer + keyPosition - 100;
			accessFile.seek(currentFilePointer);
			accessFile.read(fileContent, 0, fileBufferLength);
			String tempString = new String(fileContent, "ISO-8859-1");

			keyPosition = tempString.indexOf(findString);
			if (flag) {
				keyPosition = keyPosition + findString.length();
			}

		} catch (IOException e) {

		}

		return (int) currentFilePointer + keyPosition;

	}

	/**
	 * Where every thing begins.
	 * 
	 */
	public void integrateMultiLang() {
		out.println("EasyBanking : " + SOURCE_FILE_NAME_EASY_BANKING);
		out.println("HelloBank : " + SOURCE_FILE_NAME_HELLO_BANKING);
		out.println("ProjectSource : " + PROJECT_DIR);

		out.println("Method : integrateMultiLang");

		/*
		 * Execute the multi-lang integration only when the source_file_path
		 * is non-empty. This enables to integrated multi-lang for just one
		 * application.
		 */
		if(!("").equals(SOURCE_FILE_NAME_EASY_BANKING)){
			updateEasyBanking();
		}
		if(!("").equals(SOURCE_FILE_NAME_HELLO_BANKING)){
			updateHelloBanking();
		}
		
	}

	/*
	 * Main method. ONLY FOR DEBUGGING
	 */
	public static void main(String[] args) {
		ExcelParser excel = new ExcelParser(PROJECT_DIR, SOURCE_FILE_NAME_EASY_BANKING, SOURCE_FILE_NAME_HELLO_BANKING);
		//excel.assignColumnNumber(SOURCE_FILE_NAME_EASY_BANKING);
	}


	public void updateEasyBanking() {
		out.println("Method : updateEasyBanking");
		//assignColumnNumber(SOURCE_FILE_NAME_EASY_BANKING);
		updateFile(SOURCE_FILE_NAME_EASY_BANKING, Language.EN);
		updateFile(SOURCE_FILE_NAME_EASY_BANKING, Language.DE);
		updateFile(SOURCE_FILE_NAME_EASY_BANKING, Language.FR);
		updateFile(SOURCE_FILE_NAME_EASY_BANKING, Language.NL);
	}

	public void updateHelloBanking() {
		out.println("Method : updateHelloBanking");
		//assignColumnNumber(SOURCE_FILE_NAME_HELLO_BANKING);
		updateFile(SOURCE_FILE_NAME_HELLO_BANKING, Language.EN);
		updateFile(SOURCE_FILE_NAME_HELLO_BANKING, Language.DE);
		updateFile(SOURCE_FILE_NAME_HELLO_BANKING, Language.FR);
		updateFile(SOURCE_FILE_NAME_HELLO_BANKING, Language.NL);
	}

	public ExcelParser(String projectSource, String easyBankingSource, String helloBankingSource) {
		SOURCE_FILE_NAME_EASY_BANKING = easyBankingSource;
		SOURCE_FILE_NAME_HELLO_BANKING = helloBankingSource;
		PROJECT_DIR = projectSource;
		
		String regexForOrdinaryStrings = "<string name=\"([^\"]*)\">([^<]*)</string>";
		String regexForUnderlinedStrings = "<string name=\"([^\"]*)\"><u>([^<]*)</u></string>";
		
		patternForOrdinaryStrings = Pattern.compile(regexForOrdinaryStrings, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		patternForUnderlinedStrings = Pattern.compile(regexForUnderlinedStrings, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
	}
	
	private static MessageConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (MessageConsole) existing[i];
		//no console found, so create a new one
		MessageConsole myConsole = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[] { myConsole });
		return myConsole;
	}
	
	
	
	Pattern patternForOrdinaryStrings;
	Pattern patternForUnderlinedStrings;
	
	public ExcelParser(){
		String regexForOrdinaryStrings = "<string name=\"([^\"]*)\">([^<]*)</string>";
		String regexForUnderlinedStrings = "<string name=\"([^\"]*)\"><u>([^<]*)</u></string>";
		
		patternForOrdinaryStrings = Pattern.compile(regexForOrdinaryStrings, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		patternForUnderlinedStrings = Pattern.compile(regexForUnderlinedStrings, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		System.out.println("Constructor called");
	}
	
	
	public void updateMultiLangWithRawID(Path source){
		if(source.endsWith(PackageCopier.FOLDER_NAME_EASYBANKING)){
			System.out.println("EasyBanking :");
			changeDestinationFile(source.resolve("res/values/strings.xml"));
			changeDestinationFile(source.resolve("res/values-en/strings.xml"));
			changeDestinationFile(source.resolve("res/values-de/strings.xml"));
			changeDestinationFile(source.resolve("res/values-fr/strings.xml"));
			changeDestinationFile(source.resolve("res/values-nl/strings.xml"));
		} else if(source.endsWith(PackageCopier.FOLDER_NAME_HELLOBANKING)){
			changeDestinationFile(source.resolve("res/values/strings_en.xml"));
			changeDestinationFile(source.resolve("res/values-en/strings.xml"));
			changeDestinationFile(source.resolve("res/values-de/strings.xml"));
			changeDestinationFile(source.resolve("res/values-fr/strings.xml"));
			changeDestinationFile(source.resolve("res/values-nl/strings.xml"));
		};
	}

	
	private void changeDestinationFile(Path stringFile) {
		
		int startFilePointer = 0;
		int endFilePointer = 0;
		long endOfFilePointer = 0;

		StringBuilder fileContent = new StringBuilder();

		//System.out.format("REQUEST : Trying to update the destination file %s\n",destinationFile);

		try {
			
			
			File file = stringFile.toFile();

			// Check if the file exits, or throw ERROR.
			if (file.exists()) {
				accessFile = new RandomAccessFile(file, "rw");
			} else {
				out.println("ERROR : File does not exist! [Provide proper Folder structure]");
				//System.exit(0);
				throw new IOException();
			}

			startFilePointer = getFilePointerFromString(accessFile, startString, true);
			endFilePointer = getFilePointerFromString(accessFile, endString, false);

			// Reads the content that is between the StartString and EndString and stores them to a bufferBuilder.
			accessFile.seek(startFilePointer);
			byte[] middleData = new byte[endFilePointer - startFilePointer];
			accessFile.readFully(middleData);
			fileContent.append(new String(middleData));

			String content = fileContent.toString();
			
			// Replaces the original non-underlined strings with the modified content
			Matcher matcher = patternForOrdinaryStrings.matcher(content);
			content = matcher.replaceAll("<string name=\"$1\">$1</string>");
			
			// Replaces the original underlined strings with the modified content
			matcher = patternForUnderlinedStrings.matcher(content);
			content = matcher.replaceAll("<string name=\"$1\"><u>$1</u></string>");
			
			// Clear the content of the StringBuilder.
			fileContent.setLength(0);
			
			// Append the modified content to an empty StringBuilder
			fileContent.append(content);
			
			
			// Reads the content that is below the EndString and stores them to a bufferBuilder.
			accessFile.seek(endFilePointer);
			byte[] remainingData = new byte[(int) accessFile.length() - endFilePointer];
			accessFile.readFully(remainingData);
			fileContent.append(new String(remainingData));
						
			//System.out.println(fileContent);
			
			// Starts to write the content from the startString.
			accessFile.seek(startFilePointer);

			accessFile.write(fileContent.toString().getBytes("UTF-8"));

			// Remove all content that is present after the "</resources>" tag.
			accessFile.seek(startFilePointer);
			endOfFilePointer = getFilePointerFromString(accessFile, "</resources>", true);
			accessFile.setLength(endOfFilePointer);

		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} finally {
			try {
				accessFile.close();
			} catch (IOException e) {

			}
		}
	}
	
	/**
	 * Method to copy the strings.xml to respective values-XX-car folder
	 */
	public void copyNormalStringsToCarStrings(){
		try {
			Files.copy(Paths.get(PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values-en/strings.xml"), Paths.get(PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values-en-car/strings.xml"), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(Paths.get(PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values-de/strings.xml"), Paths.get(PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values-de-car/strings.xml"), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(Paths.get(PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values-fr/strings.xml"), Paths.get(PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values-fr-car/strings.xml"), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(Paths.get(PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values-nl/strings.xml"), Paths.get(PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values-nl-car/strings.xml"), StandardCopyOption.REPLACE_EXISTING);
			
			Files.copy(Paths.get(PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values-en/strings.xml"), Paths.get(PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values-en-car/strings.xml"), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(Paths.get(PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values-de/strings.xml"), Paths.get(PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values-de-car/strings.xml"), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(Paths.get(PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values-fr/strings.xml"), Paths.get(PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values-fr-car/strings.xml"), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(Paths.get(PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values-nl/strings.xml"), Paths.get(PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values-nl-car/strings.xml"), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			out.println("Access to values-XX-car denied / folders not found");
		}
	}
	
	/**
	 * Changes the strings.xml in values-XX-car folder to suit the needs, i.e, replaces the values with the ID.
	 */
	public void changeCarStrings(){
		changeDestinationFile(Paths.get(PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values-en-car/strings.xml"));
		changeDestinationFile(Paths.get(PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values-de-car/strings.xml"));
		changeDestinationFile(Paths.get(PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values-fr-car/strings.xml"));
		changeDestinationFile(Paths.get(PROJECT_DIR + EASYBANKING_RESOURCE_DIR + "values-nl-car/strings.xml"));
		
		changeDestinationFile(Paths.get(PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values-en-car/strings.xml"));
		changeDestinationFile(Paths.get(PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values-de-car/strings.xml"));
		changeDestinationFile(Paths.get(PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values-fr-car/strings.xml"));
		changeDestinationFile(Paths.get(PROJECT_DIR + HELLOBANKING_RESOURCE_DIR + "values-nl-car/strings.xml"));
	}
}
