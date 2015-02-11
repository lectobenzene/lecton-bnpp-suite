package com.mobility.bnpp.sonarreport.corecode.sonar;

import java.util.ArrayList;

import org.sonar.wsclient.Host;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.connectors.HttpClient4Connector;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;

import com.mobility.bnpp.sonarreport.corecode.excel.ExcelProcessor;
import com.mobility.bnpp.sonarreport.corecode.modals.SonarResultsModal;
import com.mobility.bnpp.sonarreport.plugincode.SonarReportGeneration;

public class SonarExtracter {

	private String projectKey = SonarReportGeneration.PROJECT_KEY;
	private ArrayList<SonarResultsModal> classModalList = null;
	private ArrayList<SonarResultsModal> packageModalList = null;
	private Sonar sonar;

	public SonarExtracter(ArrayList<SonarResultsModal> classModalList, ArrayList<SonarResultsModal> packageModalList) {
		this.classModalList = classModalList;
		this.packageModalList = packageModalList;
	}

	public SonarExtracter() {
	}

	public void runSonarExtraction() {
		sonar = establishConnection();

		classModalList = getFilledModalList(classModalList, 0);
		SonarReportGeneration.debugShowArrayListContent(classModalList);
		packageModalList = getFilledModalList(packageModalList, 1);
		SonarReportGeneration.debugShowArrayListContent(packageModalList);
		ExcelProcessor processor = new ExcelProcessor();
		processor.runProcessor(classModalList, packageModalList);

	}

	private ArrayList<SonarResultsModal> getFilledModalList(ArrayList<SonarResultsModal> modalList, int flag) {
		ArrayList<Integer> indicesOfInvalidModals = new ArrayList<Integer>();

		for (SonarResultsModal modal : modalList) {

			String resourceKey = projectKey + ":" + modal.getKey();
			Resource struts = sonar.find(ResourceQuery.createForMetrics(resourceKey, "violations_density", "classes", "lines", "ncloc",
					"blocker_violations", "critical_violations", "minor_violations", "major_violations"));
			if (struts == null) {
				indicesOfInvalidModals.add(modalList.indexOf(modal));
				SonarReportGeneration.showErrorDialog("Error in Entry", "There was no class or package as " + modal.getKey());
				continue;
			}

			if (struts.getMeasureValue("violations_density") != null) {
				modal.setRulesOfCompliance(struts.getMeasureValue("violations_density") + "");
			} else {
				modal.setRulesOfCompliance("0");
			}

			if (flag == 0 && struts.getMeasureIntValue("lines") != null) {
				modal.setLinesOfCode(struts.getMeasureIntValue("lines") + "");
			} else if (flag == 1 && struts.getMeasureIntValue("ncloc") != null) {
				modal.setLinesOfCode(struts.getMeasureIntValue("ncloc") + "");
			} else {
				modal.setLinesOfCode("0");
			}

			if (flag != 0) {
				modal.setNoOfClasses(struts.getMeasureFormattedValue("classes", "0"));
			}

			modal.setViolationsBlocker(struts.getMeasureFormattedValue("blocker_violations", "0"));
			modal.setViolationsCritical(struts.getMeasureFormattedValue("critical_violations", "0"));
			modal.setViolationsMajor(struts.getMeasureFormattedValue("major_violations", "0"));
			modal.setViolationsMinor(struts.getMeasureFormattedValue("minor_violations", "0"));

		}

		// Remove the faulty modal
		for (Integer indexOfInvalidModal : indicesOfInvalidModals) {
			modalList.remove((int) indexOfInvalidModal);
		}
		return modalList;
	}

	private Sonar establishConnection() {
		String url = SonarReportGeneration.URL;
		String login = "admin";
		String password = "admin";
		sonar = new Sonar(new HttpClient4Connector(new Host(url, login, password)));
		return sonar;
	}
}