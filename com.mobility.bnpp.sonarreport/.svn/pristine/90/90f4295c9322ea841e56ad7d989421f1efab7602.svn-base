package com.mobility.bnpp.sonarreport.corecode.excel;

import java.util.ArrayList;

import com.mobility.bnpp.sonarreport.corecode.modals.SonarResultsModal;
import com.mobility.bnpp.sonarreport.plugincode.SonarReportGeneration;

public class ExcelProcessor {

	public void runProcessor(ArrayList<SonarResultsModal> classModalArray, ArrayList<SonarResultsModal> packageModalArray) {

		/*
		 * If previousData is null, then Previous Data is not considered while generating the report
		 */
		SonarResultsModal previousData = null;

		if (SonarReportGeneration.PREVIOUS_SONAR_FILE != null && !"".equalsIgnoreCase(SonarReportGeneration.PREVIOUS_SONAR_FILE)) {
			ExcelParcer excelParse = new ExcelParcer();
			previousData = excelParse.extractPreviousData(SonarReportGeneration.CURRENT_SONAR_TITLE_HEADING);
		}

		if (previousData != null) {
			System.out.println(previousData.getName());
			System.out.println(previousData.getLinesOfCode());
			System.out.println(previousData.getNoOfClasses());
			System.out.println(previousData.getRulesOfCompliance());
			System.out.println(previousData.getViolationsBlocker());
			System.out.println(previousData.getViolationsCritical());
			System.out.println(previousData.getViolationsMajor());
			System.out.println(previousData.getViolationsMinor());
		}

		ExcelProducer excelPro = new ExcelProducer();
		excelPro.runExcelProducer(classModalArray, packageModalArray, previousData);

	}
}
