package com.mobility.bnpp.sonarreport.corecode.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

import com.mobility.bnpp.sonarreport.corecode.modals.MetricsMapper;
import com.mobility.bnpp.sonarreport.corecode.modals.SonarResultsModal;
import com.mobility.bnpp.sonarreport.plugincode.SonarReportGeneration;

public class ExcelProducer {

	private final static String FILE_LOCATION = SonarReportGeneration.SAVE_TO_FILE;

	private final static String SONAR_CLASS_TITLE = SonarReportGeneration.CLASS_TITLE;
	private final static String SONAR_OVERALL_TITLE = SonarReportGeneration.PACKAGE_TITLE;

	private Workbook wb;
	private FileOutputStream fileOut;
	private static CreationHelper createHelper;

	private static HSSFFont greenFont;
	private static HSSFFont boldFont;

	private static SonarResultsModal previousResultsModal;

	public ExcelProducer() {
	}

	public void runExcelProducer(ArrayList<SonarResultsModal> classModalArray, ArrayList<SonarResultsModal> packageModalArray,
			SonarResultsModal previousData) {
		ExcelProducer excelPro = new ExcelProducer();
		previousResultsModal = previousData;
		excelPro.createWorkbook(classModalArray, packageModalArray);
	}

	/**
	 * Close the workbook
	 * 
	 * @param wb
	 *            Workbook to close
	 */
	private void closeWorkbook(FileOutputStream fileOut) {
		try {
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create Workbook
	 * 
	 * @param fileLocation
	 *            The file path
	 * @param sonarResultsArray
	 * @param packageModalArray2
	 */
	private void createWorkbook(ArrayList<SonarResultsModal> classModalArray, ArrayList<SonarResultsModal> packageModalArray) {
		wb = new HSSFWorkbook();
		try {
			fileOut = new FileOutputStream(FILE_LOCATION);

			// Set green font
			greenFont = (HSSFFont) wb.createFont();
			greenFont.setColor(HSSFColor.GREEN.index);

			// Set bold font
			boldFont = (HSSFFont) wb.createFont();
			boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

			// Create helper
			createHelper = wb.getCreationHelper();

			// Create a Sheet
			Sheet sheet1 = wb.createSheet("Sonar Report");

			// Render table only if there is content
			if (!classModalArray.isEmpty()) {
				createTable(classModalArray, sheet1, 0, SONAR_CLASS_TITLE, classModalArray.size(), MetricsMapper.CLASSES_TABLE);
			}

			if (!packageModalArray.isEmpty()) {
				createTable(packageModalArray, sheet1, 12, SONAR_OVERALL_TITLE, packageModalArray.size() + 2, MetricsMapper.OVERALL_TABLE);
			}

			// Auto size the columns
			for (int index = 0; index <= packageModalArray.size() + 2; index++) {
				sheet1.autoSizeColumn(index);
			}
			for (int index = 0; index <= classModalArray.size(); index++) {
				sheet1.autoSizeColumn(index);
			}

			// Write to Excel Workbook
			wb.write(fileOut);

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found or does not exist");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Improper File");
			e.printStackTrace();
		} finally {
			// Close the workbook
			closeWorkbook(fileOut);
		}
	}

	private void createTable(ArrayList<SonarResultsModal> sonarResultsArray, Sheet sheet, int firstRowIndex, String title, int noOfColumns,
			int[] tableType) {
		CellStyle cellStyle = null;
		// Create the first row
		if (tableType == MetricsMapper.OVERALL_TABLE && previousResultsModal == null) {
			cellStyle = createFirstRow(sheet, firstRowIndex, title, noOfColumns - 1);
		} else {
			cellStyle = createFirstRow(sheet, firstRowIndex, title, noOfColumns);
		}
		// Create the remaining rows
		createContentRows(sonarResultsArray, sheet, firstRowIndex, noOfColumns, tableType, cellStyle);
	}

	private void createContentRows(ArrayList<SonarResultsModal> sonarResultsArray, Sheet sheet1, int firstRowIndex, int noOfColumns, int[] tableType,
			CellStyle cellStyle) {
		for (int rowIndex = firstRowIndex + 1, rowRelIndex = firstRowIndex + 1; (rowIndex - rowRelIndex) < tableType.length; rowIndex++) {
			Row row = sheet1.createRow(rowIndex);

			// For auto sizing the height
			row.setHeight((short) -1);

			// First column
			int colIndex = 0;
			Cell cell = row.createCell(colIndex);
			setValueInCell(cell, null, rowIndex, rowRelIndex, colIndex, cellStyle, tableType, noOfColumns);

			// Remaining columns
			colIndex = 1;
			for (SonarResultsModal modal : sonarResultsArray) {
				cell = row.createCell(colIndex);
				setValueInCell(cell, modal, rowIndex, rowRelIndex, colIndex, cellStyle, tableType, noOfColumns);
				colIndex++;
			}

			// Overall columns
			if (tableType == MetricsMapper.OVERALL_TABLE) {

				// Current Overall
				colIndex = noOfColumns - 1;
				cell = row.createCell(colIndex);
				setValueInCell(cell, null, rowIndex, rowRelIndex, colIndex, cellStyle, tableType, noOfColumns);

				// Previous Overall
				if (previousResultsModal != null) {
					colIndex++;
					cell = row.createCell(colIndex);
					setValueInCell(cell, previousResultsModal, rowIndex, rowRelIndex, colIndex, cellStyle, tableType, noOfColumns);
				}
			}
		}
	}

	private CellStyle createFirstRow(Sheet sheet, int firstRowIndex, String title, int noOfColumns) {
		// Create first row
		Row firstRow = sheet.createRow(firstRowIndex);
		Cell classTitleCell = firstRow.createCell(0);
		classTitleCell.setCellValue(title);

		// Center alignt the first row
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		// Bold font
		cellStyle.setFont(boldFont);

		// Set border
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);

		// Set background color
		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		// To make the border appear on the entire merged cell
		for (int index = 1; index <= noOfColumns; index++) {
			Cell cell = firstRow.createCell(index);
			cell.setCellStyle(cellStyle);
		}

		// Merge the first row to appropriate length
		sheet.addMergedRegion(new CellRangeAddress(firstRowIndex, firstRowIndex, 0, noOfColumns));

		classTitleCell.setCellStyle(cellStyle);
		return cellStyle;
	}

	private void setValueInCell(Cell cell, SonarResultsModal modal, int rowIndex, int rowRelIndex, int colIndex, CellStyle cellStyle,
			int[] tableType, int noOfColumns) {

		// Sets basic cell style;
		cellStyle = setBasicStyle(cellStyle);

		switch (tableType[rowIndex - rowRelIndex]) {

		case MetricsMapper.NAME:
			if (colIndex == 0) {
				publishName(cell, null, colIndex, cellStyle);
			} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns - 1)) {
				publishName(cell, SonarReportGeneration.CURRENT_SONAR_TITLE_HEADING, colIndex, cellStyle);
			} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns) && previousResultsModal != null) {
				publishName(cell, previousResultsModal.getName(), colIndex, cellStyle);
			} else {
				publishName(cell, modal.getName(), colIndex, cellStyle);
			}
			break;

		case MetricsMapper.LINES_OF_CODE:
			if (colIndex == 0) {
				publishNumberMetrics(cell, colIndex, cellStyle, "Lines of Code", noOfColumns);
			} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns - 1)) {
				publishNumberMetrics(cell, colIndex, cellStyle, null, noOfColumns);
			} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns) && previousResultsModal != null) {
				publishNumberMetrics(cell, colIndex, cellStyle, previousResultsModal.getLinesOfCode(), noOfColumns);
			} else {
				publishNumberMetrics(cell, colIndex, cellStyle, modal.getLinesOfCode(), noOfColumns);
			}
			break;

		case MetricsMapper.CLASSES:
			if (colIndex == 0) {
				publishNumberMetrics(cell, colIndex, cellStyle, "Classes", noOfColumns);
			} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns - 1)) {
				publishNumberMetrics(cell, colIndex, cellStyle, null, noOfColumns);
			} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns) && previousResultsModal != null) {
				publishNumberMetrics(cell, colIndex, cellStyle, previousResultsModal.getNoOfClasses(), noOfColumns);
			} else {
				publishNumberMetrics(cell, colIndex, cellStyle, modal.getNoOfClasses(), noOfColumns);
			}
			break;

		case MetricsMapper.COMPLAINCE:

			if (colIndex == 0) {
				publishCompliance(cell, "Compliance %", colIndex, cellStyle, noOfColumns);
			} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns - 1)) {
				publishCompliance(cell, null, colIndex, cellStyle, noOfColumns);
			} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns) && previousResultsModal != null) {
				publishCompliance(cell, previousResultsModal.getRulesOfCompliance(), colIndex, cellStyle, noOfColumns);
			} else {
				publishCompliance(cell, modal.getRulesOfCompliance(), colIndex, cellStyle, noOfColumns);
			}
			break;

		case MetricsMapper.VIOLATIONS:
			publishViolations(cell, colIndex, cellStyle, noOfColumns, tableType);
			break;

		case MetricsMapper.VIOLATIONS_BLOCKER:
			if (colIndex == 0) {
				publishNumberMetrics(cell, colIndex, cellStyle, "Blocker", noOfColumns);
			} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns - 1)) {
				publishNumberMetrics(cell, colIndex, cellStyle, null, noOfColumns);
			} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns) && previousResultsModal != null) {
				publishNumberMetrics(cell, colIndex, cellStyle, previousResultsModal.getViolationsBlocker(), noOfColumns);
			} else {
				publishNumberMetrics(cell, colIndex, cellStyle, modal.getViolationsBlocker(), noOfColumns);
			}
			break;

		case MetricsMapper.VIOLATIONS_CRITICAL:
			if (colIndex == 0) {
				publishNumberMetrics(cell, colIndex, cellStyle, "Critical", noOfColumns);
			} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns - 1)) {
				publishNumberMetrics(cell, colIndex, cellStyle, null, noOfColumns);
			} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns) && previousResultsModal != null) {
				publishNumberMetrics(cell, colIndex, cellStyle, previousResultsModal.getViolationsCritical(), noOfColumns);
			} else {
				publishNumberMetrics(cell, colIndex, cellStyle, modal.getViolationsCritical(), noOfColumns);
			}
			break;

		case MetricsMapper.VIOLATIONS_MAJOR:
			if (colIndex == 0) {
				publishNumberMetrics(cell, colIndex, cellStyle, "Major", noOfColumns);
			} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns - 1)) {
				publishNumberMetrics(cell, colIndex, cellStyle, null, noOfColumns);
			} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns) && previousResultsModal != null) {
				publishNumberMetrics(cell, colIndex, cellStyle, previousResultsModal.getViolationsMajor(), noOfColumns);
			} else {
				publishNumberMetrics(cell, colIndex, cellStyle, modal.getViolationsMajor(), noOfColumns);
			}
			break;

		case MetricsMapper.VIOLATIONS_MINOR:
			if (colIndex == 0) {
				publishNumberMetrics(cell, colIndex, cellStyle, "Minor", noOfColumns);
			} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns - 1)) {
				publishNumberMetrics(cell, colIndex, cellStyle, null, noOfColumns);
			} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns) && previousResultsModal != null) {
				publishNumberMetrics(cell, colIndex, cellStyle, previousResultsModal.getViolationsMinor(), noOfColumns);
			} else {
				publishNumberMetrics(cell, colIndex, cellStyle, modal.getViolationsMinor(), noOfColumns);
			}
			break;

		default:
			break;
		}

	}

	private CellStyle setBasicStyle(CellStyle cellStyle) {
		// Reset the cell style
		cellStyle = wb.createCellStyle();
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		return cellStyle;
	}

	private void publishNumberMetrics(Cell cell, int colIndex, CellStyle cellStyle, String content, int noOfColumns) {
		if (colIndex == 0) {
			cellStyle.setWrapText(true);
			cell.setCellValue(content);
		} else if (content == null) {
			cellStyle.setFont(greenFont);
			cell.setCellType(Cell.CELL_TYPE_FORMULA);
			cell.setCellFormula(getFormulaOverallSum(cell, noOfColumns));
		} else {
			cellStyle.setFont(greenFont);
			cell.setCellValue(Integer.parseInt(content));
		}
		cell.setCellStyle(cellStyle);
	}

	private String getFormulaOverallSum(Cell cell, int noOfColumns) {
		CellReference ref = new CellReference(cell);
		// Adding 1, because in col and row are 1 based
		String formula = CellReference.convertNumToColString(1) + (cell.getRowIndex() + 1) + ":"
				+ CellReference.convertNumToColString(ref.getCol() - 1) + (cell.getRowIndex() + 1);

		formula = "SUM(" + formula + ")";
		return formula;
	}

	private void publishViolations(Cell cell, int colIndex, CellStyle cellStyle, int noOfColumns, int[] tableType) {
		if (colIndex == 0) {
			cellStyle.setWrapText(true);
			cell.setCellValue("Violations(sum \nof blocker+critical\n+major+ minor)");
		} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns - 1)) {
			cellStyle.setFont(greenFont);
			cell.setCellType(Cell.CELL_TYPE_FORMULA);
			cell.setCellFormula(getFormulaOverallSum(cell, noOfColumns));
		} else if (tableType == MetricsMapper.OVERALL_TABLE && colIndex == (noOfColumns) && previousResultsModal != null) {
			cellStyle.setFont(greenFont);
			cell.setCellType(Cell.CELL_TYPE_FORMULA);
			cell.setCellFormula(getFormulaViolationsSum(cell));
		} else {
			cellStyle.setFont(greenFont);
			cell.setCellType(Cell.CELL_TYPE_FORMULA);
			cell.setCellFormula(getFormulaViolationsSum(cell));
		}
		cell.setCellStyle(cellStyle);
	}

	private void publishCompliance(Cell cell, String string, int colIndex, CellStyle cellStyle, int noOfColumns) {
		cellStyle.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		if (colIndex == 0) {
			cellStyle.setWrapText(true);
			cell.setCellValue("Compliance %");
		} else if (string == null) {
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.00%"));
			cellStyle.setFont(greenFont);
			cell.setCellType(Cell.CELL_TYPE_FORMULA);
			cell.setCellFormula(getFormulaOverallSum(cell, noOfColumns) + "/" + (noOfColumns - 2));
		} else {
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.00%"));
			cellStyle.setFont(greenFont);
			cell.setCellValue(Float.parseFloat(string) / 100);
		}
		cell.setCellStyle(cellStyle);
	}

	private void publishName(Cell cell, String title, int colIndex, CellStyle cellStyle) {
		cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);
		cellStyle.setFont(boldFont);

		if (colIndex == 0) {
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MMM"));
			cell.setCellValue(new Date());
		} else {
			cell.setCellValue(replaceSpaceWithReturn(title));
		}
		cell.setCellStyle(cellStyle);
	}

	private String replaceSpaceWithReturn(String name) {
		System.out.println(name.replace(" ", "\n"));
		return name.replace(" ", "\n");
	}

	private String getFormulaViolationsSum(Cell cell) {
		CellReference ref = new CellReference(cell);
		String formula = "";
		for (int index = 1; index <= 4; index++) {
			// row and col are 0 based. But when converted to Alphabets, its 1
			// based. Hence 1 is added
			formula += CellReference.convertNumToColString(ref.getCol()) + ((cell.getRowIndex() + 1) + index) + ",";
		}

		// Remove the last comma
		formula = "SUM(" + formula.substring(0, formula.length() - 1) + ")";
		return formula;
	}

}
