/**
 * 
 */
package com.mobility.bnpp.sonarreport.corecode.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;

import com.mobility.bnpp.sonarreport.corecode.modals.SonarResultsModal;
import com.mobility.bnpp.sonarreport.plugincode.SonarReportGeneration;

/**
 * @author Saravana
 * 
 */
public class ExcelParcer {

	private HSSFWorkbook wb;

	public SonarResultsModal extractPreviousData(String columnTitle) {
		columnTitle = stripWhiteSpace(columnTitle);
		FileInputStream fileInputStream = null;
		CellReference cellRef = null;
		SonarResultsModal previousDataModal = new SonarResultsModal();

		try {
			fileInputStream = new FileInputStream(new File(SonarReportGeneration.PREVIOUS_SONAR_FILE));
			POIFSFileSystem previousFile = new POIFSFileSystem(fileInputStream);
			wb = new HSSFWorkbook(previousFile);

			Sheet sheet1 = wb.getSheetAt(0);
			for (Row row : sheet1) {
				for (Cell cell : row) {
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						System.out.println("-----*****----");
						System.out.println("Current : " + stripWhiteSpace(cell.getStringCellValue()));
						System.out.println("Previous : " + columnTitle);
						if (stripWhiteSpace(cell.getStringCellValue()).equalsIgnoreCase(columnTitle)) {
							cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
						}
						break;
					default:
						System.out.println();
					}
				}
			}
			if (cellRef != null) {
				System.out.println("Pointer - " + cellRef.formatAsString());

				int headingRowIndex = cellRef.getRow();
				Row row = sheet1.getRow(headingRowIndex);
				FormulaEvaluator formulaEval = wb.getCreationHelper().createFormulaEvaluator();

				// Name
				previousDataModal.setName(SonarReportGeneration.PREVIOUS_SONAR_TITLE_HEADING);

				// Lines of code
				row = sheet1.getRow(headingRowIndex + 1);
				previousDataModal.setLinesOfCode((int) Double.parseDouble(formulaEval.evaluate(row.getCell(cellRef.getCol())).formatAsString()) + "");

				// Classes
				row = sheet1.getRow(headingRowIndex + 2);
				previousDataModal.setNoOfClasses((int) Double.parseDouble(formulaEval.evaluate(row.getCell(cellRef.getCol())).formatAsString()) + "");

				// RulesOfCompliance
				row = sheet1.getRow(headingRowIndex + 3);
				previousDataModal.setRulesOfCompliance(Double.parseDouble(formulaEval.evaluate(row.getCell(cellRef.getCol())).formatAsString()) * 100
						+ "");

				// Violations
				// Calculated dynamically

				// Blocker
				row = sheet1.getRow(headingRowIndex + 5);
				previousDataModal.setViolationsBlocker((int) Double.parseDouble(formulaEval.evaluate(row.getCell(cellRef.getCol())).formatAsString())
						+ "");

				// Crtical
				row = sheet1.getRow(headingRowIndex + 6);
				previousDataModal
						.setViolationsCritical((int) Double.parseDouble(formulaEval.evaluate(row.getCell(cellRef.getCol())).formatAsString()) + "");

				// Major
				row = sheet1.getRow(headingRowIndex + 7);
				previousDataModal.setViolationsMajor((int) Double.parseDouble(formulaEval.evaluate(row.getCell(cellRef.getCol())).formatAsString())
						+ "");

				// Minor
				row = sheet1.getRow(headingRowIndex + 8);
				previousDataModal.setViolationsMinor((int) Double.parseDouble(formulaEval.evaluate(row.getCell(cellRef.getCol())).formatAsString())
						+ "");

			} else {
				SonarReportGeneration.showErrorDialog("Data Not Found", "Previous Sonar Details Not Found");
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found or does not exist");
			SonarReportGeneration.showErrorDialog("Warning", "File Does not exist");
			e.printStackTrace();

			// Go ahead and generate report without the Previous Report field
			return null;
		} catch (IOException e) {
			System.out.println("Improper File");
			SonarReportGeneration.showErrorDialog("Warning", "Improper File");
			e.printStackTrace();

			// Go ahead and generate report without the Previous Report field
			return null;
		} finally {
			// Close the workbook
			closeWorkbook(fileInputStream);
		}
		return previousDataModal;
	}

	private String stripWhiteSpace(String columnTitle) {
		columnTitle = columnTitle.replace("\n", "").replace("\t", "").replace("\r", "").replace(" ", "");
		return columnTitle;
	}

	/**
	 * Close the workbook
	 * 
	 * @param wb
	 *            Workbook to close
	 */
	private void closeWorkbook(FileInputStream fileInputStream) {
		try {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
