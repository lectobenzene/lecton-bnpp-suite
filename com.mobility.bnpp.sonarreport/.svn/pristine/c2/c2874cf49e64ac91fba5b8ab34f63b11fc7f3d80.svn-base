package com.mobility.bnpp.sonarreport.plugincode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.mobility.bnpp.sonarreport.Activator;
import com.mobility.bnpp.sonarreport.corecode.modals.SonarResultsModal;
import com.mobility.bnpp.sonarreport.corecode.sonar.SonarExtracter;
import com.mobility.bnpp.sonarreport.preferences.PreferenceConstants;

public class SonarReportGeneration extends AbstractHandler implements IHandler {

	public static String URL;
	public static String PROJECT_KEY;

	public static String SAVE_TO;
	public static String FILE_NAME;
	public static String CLASS_TITLE;
	public static String PACKAGE_TITLE;
	public static String SAVE_TO_FILE;

	public static String PREVIOUS_SONAR_FILE;
	
	public static String CURRENT_SONAR_TITLE_HEADING;
	public static String PREVIOUS_SONAR_TITLE_HEADING;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		URL = store.getString(PreferenceConstants.URL);
		PROJECT_KEY = store.getString(PreferenceConstants.PROJECT_KEY);
		SAVE_TO = store.getString(PreferenceConstants.SAVE_TO);
		FILE_NAME = store.getString(PreferenceConstants.FILE_NAME) + ".xls";
		CLASS_TITLE = store.getString(PreferenceConstants.CLASS_TITLE);
		PACKAGE_TITLE = store.getString(PreferenceConstants.PACKAGE_TITLE);

		CURRENT_SONAR_TITLE_HEADING = store.getString(PreferenceConstants.CURRENT_OVERALL);
		PREVIOUS_SONAR_TITLE_HEADING = store.getString(PreferenceConstants.PREVIOUS_OVERALL);
		
		InputDialog dialog = new InputDialog(window.getShell());

		InputDialog.setSonarClassList(getModalListFromPreferences(Activator.getDefault().getPreferenceStore()
				.getString(PreferenceConstants.CLASS_DATA)));
		InputDialog.setSonarPackageList(getModalListFromPreferences(Activator.getDefault().getPreferenceStore()
				.getString(PreferenceConstants.PACKAGE_DATA)));

		dialog.open();

		if (dialog.isOKPressed()) {

			SAVE_TO_FILE = dialog.getSaveToPath();
			PREVIOUS_SONAR_FILE = dialog.getPreviousSonarFilePath();
			
			ArrayList<SonarResultsModal> classArray = InputDialog.getSonarClassList();
			ArrayList<SonarResultsModal> packageArray = InputDialog.getSonarPackageList();

			setPreferencesFromModalList(classArray, PreferenceConstants.CLASS_DATA);
			setPreferencesFromModalList(packageArray, PreferenceConstants.PACKAGE_DATA);

			SonarExtracter extractor = new SonarExtracter(classArray, packageArray);
			debugShowArrayListContent(classArray);
			debugShowArrayListContent(packageArray);
			extractor.runSonarExtraction();
		}

		return null;
	}

	public static void debugShowArrayListContent(ArrayList<SonarResultsModal> arrayList) {
		for(SonarResultsModal modal : arrayList){
			System.out.println("--------------------------");
			System.out.println("Name : " + modal.getName());
			System.out.println("Key : " + modal.getKey());
		}
	}

	private void setPreferencesFromModalList(ArrayList<SonarResultsModal> arrayList, String prefKey) {
		if (arrayList.isEmpty()) {
			Activator.getDefault().getPreferenceStore().setValue(prefKey, "");
		} else {
			StringBuilder builder = new StringBuilder("");

			for (SonarResultsModal modal : arrayList) {
				builder.append(modal.getName() + "$" + modal.getKey());
				builder.append(":");
			}

			// Remove the last colon
			String string = builder.toString().substring(0, builder.length() - 1);
			System.out.println(string);
			Activator.getDefault().getPreferenceStore().setValue(prefKey, string);

			System.out.println("IMM" + Activator.getDefault().getPreferenceStore().getString(prefKey));

		}
	}

	private ArrayList<SonarResultsModal> getModalListFromPreferences(String value) {
		System.out.println("GetModalList");
		ArrayList<SonarResultsModal> modalList = new ArrayList<SonarResultsModal>();
		SonarResultsModal modal;

		System.out.println("Value is " + value);
		if (value != null && !"".equalsIgnoreCase(value)) {
			String[] keyValuePairs = value.split(":");
			for (String keyValuePair : keyValuePairs) {
				String[] nameAndKeys = keyValuePair.split("\\$");
				for (String nameAndKey : nameAndKeys) {
					System.out.println("Debug - " + nameAndKey);
				}
				modal = new SonarResultsModal(nameAndKeys[0], nameAndKeys[1]);
				modalList.add(modal);
			}
		}
		return modalList;
	}

	/**
	 * Method to convert string into date/time characters based on the
	 * characters between the %% %% string.
	 * 
	 * @param fileName
	 *            The string that has the %%aaaaaa%% in it.
	 * @return the string where contents between the %% %% is replaced with the
	 *         date format of the string.
	 */
	public static String convertStringWithDate(String fileName) {

		Pattern fileWithDatePattern = Pattern.compile("(?:%%)([^%]*)(?:%%)", Pattern.DOTALL);
		Matcher matcher = fileWithDatePattern.matcher(fileName);

		while (matcher.find()) {
			fileName = fileName.replace(matcher.group(0), getDateFromStringFormat(matcher.group(1)));
		}

		return fileName;

	}

	/**
	 * Returns the current date as a String in any particular format
	 * 
	 * @param dateFormat
	 *            The DateFormat string. eg: MMMdd_HH_mm
	 * @return The current date in the required format as a String
	 */
	public static String getDateFromStringFormat(String dateFormat) {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

		try {
			simpleDateFormat.applyPattern(dateFormat);
		} catch (Exception e) {
			return "invalid_pattern";
		}

		return simpleDateFormat.format(date);
	}

	public static void showErrorDialog(String title, String message) {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		MessageBox alertDialog = new MessageBox(window.getShell(), SWT.ICON_ERROR | SWT.OK);
		alertDialog.setText(title);
		alertDialog.setMessage(message);
		alertDialog.open();
	}
}
