package experiments

import org.apache.poi.hssf.usermodel.HSSFCell;


/**
 * Returns the content of an Excel Cell.
 *
 * @param cell The cell from which data has to be taken
 * @param formulaEval Formula evaluator to resolve cells of type formula
 * @return The String value of the content in the cell
 */
private String getCellValue(HSSFCell cell, formulaEval) {
	switch (cell.getCellType()) {

		case Cell.CELL_TYPE_STRING:
			return cell.getRichStringCellValue().getString()

		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				println 'Date - '
				return cell.getDateCellValue()
			} else {
			println 'Num - '
				return cell.getNumericCellValue()
			}

		case Cell.CELL_TYPE_BOOLEAN:
		println 'Boo - '
			return cell.getBooleanCellValue()

		case Cell.CELL_TYPE_FORMULA:
		println 'Form - '
			return formulaEval.evaluate(cell).formatAsString()

		default:
			println 'Nothing'
	}
}