package com.mobility.bnpp.sonarreport.corecode.modals;

public class SonarResultsModal {

	// The name to be shown in excel
	private String name;

	// The Identifier - the actual class name
	private String key;

	private String linesOfCode;
	private String rulesOfCompliance;
	private String noOfClasses;

	private String violationsBlocker;
	private String violationsCritical;
	private String violationsMajor;
	private String violationsMinor;

	public SonarResultsModal(String name, String key) {
		this.name = name;
		this.key = key;
	}

	public SonarResultsModal() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLinesOfCode() {
		return linesOfCode;
	}

	public void setLinesOfCode(String linesOfCode) {
		this.linesOfCode = linesOfCode;
	}

	public String getRulesOfCompliance() {
		return rulesOfCompliance;
	}

	public void setRulesOfCompliance(String rulesOfCompliance) {
		this.rulesOfCompliance = rulesOfCompliance;
	}

	public String getNoOfClasses() {
		return noOfClasses;
	}

	public void setNoOfClasses(String noOfClasses) {
		this.noOfClasses = noOfClasses;
	}

	public String getViolationsBlocker() {
		return violationsBlocker;
	}

	public void setViolationsBlocker(String violationsBlocker) {
		this.violationsBlocker = violationsBlocker;
	}

	public String getViolationsCritical() {
		return violationsCritical;
	}

	public void setViolationsCritical(String violationsCritical) {
		this.violationsCritical = violationsCritical;
	}

	public String getViolationsMajor() {
		return violationsMajor;
	}

	public void setViolationsMajor(String violationsMajor) {
		this.violationsMajor = violationsMajor;
	}

	public String getViolationsMinor() {
		return violationsMinor;
	}

	public void setViolationsMinor(String violationsMinor) {
		this.violationsMinor = violationsMinor;
	}

}
