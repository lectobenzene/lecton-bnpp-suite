package com.mobility.bnpp.sonarreport.corecode.modals;

public class MetricsMapper {

	public static final int NAME = 0;
	public static final int LINES_OF_CODE = 1;
	public static final int CLASSES = 2;
	public static final int COMPLAINCE = 3;
	public static final int VIOLATIONS = 4;
	public static final int VIOLATIONS_BLOCKER = 5;
	public static final int VIOLATIONS_CRITICAL = 6;
	public static final int VIOLATIONS_MAJOR = 7;
	public static final int VIOLATIONS_MINOR = 8;

	public static final int[] CLASSES_TABLE = { NAME, LINES_OF_CODE, COMPLAINCE, VIOLATIONS, VIOLATIONS_BLOCKER, VIOLATIONS_CRITICAL,
			VIOLATIONS_MAJOR, VIOLATIONS_MINOR };
	
	public static final int[] OVERALL_TABLE = { NAME, LINES_OF_CODE, CLASSES, COMPLAINCE, VIOLATIONS, VIOLATIONS_BLOCKER, VIOLATIONS_CRITICAL,
		VIOLATIONS_MAJOR, VIOLATIONS_MINOR };
}
