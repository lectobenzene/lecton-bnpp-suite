package corecode

import corecode.MultiLangController;
import groovy.xml.MarkupBuilder

/**
 * Handles the logic of reading from the source file and then constructing the STRING tags using MarkupBuilder
 * and making it ready for writing to the destination file
 * 
 * @author Saravana T / 587418
 */
class MultiLangReader {

	/**
	 * Method to get the contents of the file formatted with MarkupBuilder
	 * 
	 * @param filePath The String that denotes the location of the file
	 * @param underlinedArrayList The array list that has the MultiLang IDs of all entries that should have the UNDERLINE tag
	 * @return A StringWriter that has the contents of the file
	 */
	def getFileContents(filePath, ArrayList<String> underlinedArrayList) {

		def multiLangFile = new File(filePath)

		def multiLangWriter = new StringWriter()
		def multiLangBuilder = new MarkupBuilder(multiLangWriter)
		multiLangBuilder.setDoubleQuotes(true)

		// logic to create a STRING entry per line in the MultiLang file
		multiLangFile.eachLine(MultiLangController.encodingRead){

			/* Split the line based on ','
			 * Not that the ',' should be followed by '"' character
			 * A positive lookaround is used
			 */
			def multiLangList = it.split(/,(?=")/)

			if(it.find(/\d{5}/)){
				println it
				if(underlinedArrayList.contains(multiLangList[0])){
					multiLangBuilder.string(name:"_${multiLangList[0]}"){ u "${multiLangList[1].replaceAll(/"/,'').replaceAll(/'/,/\\'/)}" }
				}else{
					multiLangBuilder.string(name:"_${multiLangList[0]}" , "${multiLangList[1].replaceAll(/"/,'').replaceAll(/'/,/\\'/)}")
				}
			}else{
				/* if the multiLang file is not properly constructed and
				 * if the line does not start with the 'XXXXX' pattern then
				 * it is an error. This might happen if there is an invisible (non pritable)
				 * 'red upside down question mark' in the file
				 */
				multiLangBuilder.string(name:"_ERROR", "$it")
			}
		}
		return multiLangWriter
	}
}
