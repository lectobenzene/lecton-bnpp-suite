package experiments


import groovy.xml.MarkupBuilder

import org.apache.poi.hssf.usermodel.HSSFAnchor
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory


/**
 * Back up of class
 * This is a working copy.
 * Currently, for n languages, n cases are put in switch statement.
 * Trying to modify this, so that languages can be dynamically handled
 * 
 * @author Saravana
 *
 */
class ExcelReaders {

	def sourceFile
	def columnNames
	def underlinedList
	def ommitedList
	def sheetNo

	ExcelReaders(sourceFile, sheetNo, columnNames, underlinedList, ommitedList){
		this.sourceFile  = sourceFile
		this.columnNames = columnNames
		this.underlinedList = underlinedList
		this.ommitedList = ommitedList
		this.sheetNo = sheetNo
	}

	public runExcelReader(){
		def workbook = WorkbookFactory.create(new File(sourceFile))
		def sheet = workbook.getSheetAt(sheetNo)

		return processSheet(sheet, columnNames, underlinedList)
	}

	/**
	 * 
	 * @param sheet The sheet in the Excel document to process
	 */
	private processSheet(sheet, columnNames, underlinedList) {

		def columnMap = [:]
		def multiLangID = null
		def value

		def writerEN = new StringWriter()
		def writerFR = new StringWriter()
		def writerNL = new StringWriter()
		def writerNLB = new StringWriter()
		def writerDE = new StringWriter()
		def writerERR = new StringWriter()

		def builderEN = new MarkupBuilder(writerEN)
		def builderFR = new MarkupBuilder(writerFR)
		def builderNL = new MarkupBuilder(writerNL)
		def builderNLB = new MarkupBuilder(writerNLB)
		def builderDE = new MarkupBuilder(writerDE)
		def builderERR = new MarkupBuilder(writerERR)

		builderEN.setDoubleQuotes(true)
		builderFR.setDoubleQuotes(true)
		builderNL.setDoubleQuotes(true)
		builderNLB.setDoubleQuotes(true)
		builderDE.setDoubleQuotes(true)
		builderERR.setDoubleQuotes(true)

		for(row in sheet){

			/* Get a Map of ColumnName : ColumnId
			 * This is for the first row only.
			 */
			if(row.getRowNum() == 0){
				for(cell in row){
					cell.setCellType(Cell.CELL_TYPE_STRING)
					columnNames.each {
						if(it == getCellValue(cell).trim()){
							columnMap[it] = cell.getColumnIndex()
						}
						
					}
				}
				// Since the 1st row is handled, continue with 2nd row
				continue
			}
			cellLoop:
			for(cell in row){
				cell.setCellType(Cell.CELL_TYPE_STRING)
				value = getCellValue(cell)
				switch(cell.getColumnIndex()){
					case columnMap['TEXT ID']:
						if(ommitedList.find{it == value}){
							break cellLoop
						}
						multiLangID = value
						break

					case columnMap['EN']:
						appendBuilder(builderEN, multiLangID, value, underlinedList)
						break

					case columnMap['NL']:
						appendBuilder(builderNL, multiLangID, value, underlinedList)
						break
					
					case columnMap['NLB']:
						appendBuilder(builderNLB, multiLangID, value, underlinedList)
						break

					case columnMap['FR']:
						appendBuilder(builderFR, multiLangID, value, underlinedList)
						break

					case columnMap['DE']:
						appendBuilder(builderDE, multiLangID, value, underlinedList)
						break
				}
			}
		}
		return ['EN':replaceChar(writerEN), 'FR':replaceChar(writerFR), 'NL':replaceChar(writerNL), 'NLB':replaceChar(writerNLB), 'DE':replaceChar(writerDE)]
	}

	/**
	 * 
	 * @param writer
	 * @return
	 */
	private replaceChar(writer) {
		return writer.toString().replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&")
	}

	/**
	 * 
	 * @param builder
	 * @param multiLangID
	 * @param value
	 * @param underlinedList
	 * @return
	 */
	private appendBuilder(builder, multiLangID, value, underlinedList) {
		if(value.find(/[&<>]/)){
			builder.string(name:"_${multiLangID}" , "<![CDATA[${value.replaceAll(/"/,'').replaceAll(/'/,/\\'/)}]]>")
		}else{
			if(underlinedList.contains(multiLangID)){
				builder.string(name:"_${multiLangID}"){ u "${value.replaceAll(/"/,'').replaceAll(/'/,/\\'/)}" }
			}else{
				builder.string(name:"_${multiLangID}" , "${value.replaceAll(/"/,'').replaceAll(/'/,/\\'/)}")
			}
		}
	}

	/**
	 * Returns the content of an Excel Cell.
	 * 
	 * @param cell The cell from which data has to be taken
	 * @return The String value of the content in the cell
	 */
	private String getCellValue(cell) {
		return cell.getRichStringCellValue().getString()
	}
}
