package corecode

import corecode.MultiLangController;

/**
 * The central controller that is accessed from the External environment (JAVA) for 
 * MultiLang integration
 * 
 * @author Saravana T / 587418
 */
class MultiLangController {

	/**
	 * Encoding used to read from source file
	 */
	public static String encodingRead = 'UTF-8'
	/**
	 * Encoding used to write into destination file
	 */
	public static String encodingWrite = 'UTF-8'

	/**
	 * Constructor
	 * 
	 * @param encodingRead The encoding used to read from source file</br>
	 * If the encoding is not mentioned, then 'UTF-8' will be used
	 * 
	 * @param encodingWrite The encoding used to write into destination file</br>
	 * If the encoding is not mentioned, then 'UTF-8' will be used
	 */
	MultiLangController(String encodingRead, String encodingWrite){
		this.encodingRead = encodingRead
		this.encodingWrite = encodingWrite
	}
	
	/**
	 * 
	 * @param sourceFilePath The String that denotes the location of the source file
	 * @param destinationFilePath The String that denotes the location of the destination file
	 * @param underlinedArrayList The array list that has the MultiLang IDs of all entries that should have the UNDERLINE tag
	 */
	def updateMultiLang(String sourceFilePath, String destinationFilePath, ArrayList<String> underlinedArrayList){
		def reader = new MultiLangReader()
		def writer = new MultiLangWriter()

		def fileContents = reader.getFileContents(sourceFilePath, underlinedArrayList)
		writer.writeFileContents(destinationFilePath, fileContents)
	}
}
