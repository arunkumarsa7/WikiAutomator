package com.automate.wiki.helper;

import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_ENVIRONMENT_PROD;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_CONFIG_FILE_NAME;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_CONVERSION_DATE_FORMAT;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_DESTINATION_FILE_LOCATION;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_DETAILED_SUMMARY_REPORT_UPTO_YEAR;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_DISABLE_GPU;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_ENTRY_ELEMENT_XPATH;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_ENVIRONMENT;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_GENERATE_DETAILED_SUMMARY_REPORT;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_HEADLESS_MODE;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_ITERATION_AUTHOR_ELEMENT_XPATH;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_ITERATION_DATE_ELEMENT_XPATH;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_ITERATION_DESCRIPTION_ELEMENT_XPATH;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_ITERATION_DONE_FOR_LINUX;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_ITERATION_DONE_FOR_WINDOWS;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_ITERATION_ELEMENT_XPATH;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_ITERATION_LINUX_OUT_LOCATION;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_ITERATION_TARGET_DATE_FORMAT;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_ITERATION_WIKI_DISPLAY_DATE_FORMAT;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_ITERATION_WINDOWS_OUT_LOCATION;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_ITERATION_WORKSPACE;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_NEXT_ITERATION_DEFAULT_COMPLETION_TIME;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_PARENT_ELEMENT_XPATH;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_QUIT_WEB_DRIVER_AFTER_EXECUTION;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_RESOURCES_LOCATION;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_SOURCE_URL;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_SUMMARY_DATE_FORMAT;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_TARGET_DAY;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_TARGET_TIMEZONE;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_WEB_DRIVER;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_WEB_DRIVER_NAME;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_WEB_DRIVER_WAIT;

import java.io.File;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import com.automate.wiki.util.ConfigLoader;
import com.automate.wiki.util.ExternalResourceLoader;
import com.automate.wiki.util.WikiAutomatorUtils;

public class ConfigReader {

	private static ResourceBundle localResourceBundle;

	private static ResourceBundle appResourceBundle;

	private ConfigReader() {

	}

	static {
		localResourceBundle = ConfigLoader.getLocalConfiguration();
		appResourceBundle = ConfigLoader.getAppConfiguration();
	}

	public static String getConfigFileName() {
		return localResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_CONFIG_FILE_NAME);
	}

	public static String getDestinationLocation() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_DESTINATION_FILE_LOCATION);
	}

	public static String getIterationTargetDateFormat() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_ITERATION_TARGET_DATE_FORMAT);
	}

	public static String getSourceUrl() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_SOURCE_URL);
	}

	public static String getWebDriver() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_WEB_DRIVER);
	}

	public static String getWebDriverName() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_WEB_DRIVER_NAME);
	}

	public static int getNextIterationDefaultCompletionTime() {
		return Integer
				.parseInt(appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_NEXT_ITERATION_DEFAULT_COMPLETION_TIME));
	}

	public static String getOutputTargetDay() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_TARGET_DAY);
	}

	public static String getResourcesLocation() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_RESOURCES_LOCATION);
	}

	public static boolean isDisableGPU() {
		return Boolean.parseBoolean(appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_DISABLE_GPU));
	}

	public static boolean isHeadlessMode() {
		return Boolean.parseBoolean(appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_HEADLESS_MODE));
	}

	public static int getWebDriverWaitTill() {
		return Integer.parseInt(appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_WEB_DRIVER_WAIT));
	}

	public static int getDetailedSummaryReportUptoYear() {
		return Integer.parseInt(appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_DETAILED_SUMMARY_REPORT_UPTO_YEAR));
	}

	public static String getParentElementXPath() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_PARENT_ELEMENT_XPATH);
	}

	public static String getEntryElementXPath() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_ENTRY_ELEMENT_XPATH);
	}

	public static String getIterationElementXPath() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_ITERATION_ELEMENT_XPATH);
	}

	public static String getIterationDateElementXPath() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_ITERATION_DATE_ELEMENT_XPATH);
	}

	public static String getIterationDescriptionElementXPath() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_ITERATION_DESCRIPTION_ELEMENT_XPATH);
	}

	public static String getIterationAuthorElementXPath() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_ITERATION_AUTHOR_ELEMENT_XPATH);
	}

	public static String getTargetTimezone() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_TARGET_TIMEZONE);
	}

	public static String getConversionDateFormat() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_CONVERSION_DATE_FORMAT);
	}

	public static String getSummaryDateFormat() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_SUMMARY_DATE_FORMAT);
	}

	public static String getIterationWorkspace() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_ITERATION_WORKSPACE);
	}

	public static boolean isIterationDoneLinux() {
		return Boolean.parseBoolean(appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_ITERATION_DONE_FOR_LINUX));
	}

	public static boolean isIterationDoneWindows() {
		return Boolean.parseBoolean(appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_ITERATION_DONE_FOR_WINDOWS));
	}

	public static boolean isgGenerateDetailedSummaryReport() {
		return Boolean
				.parseBoolean(appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_GENERATE_DETAILED_SUMMARY_REPORT));
	}

	public static boolean isQuitWebDriverAfterExecution() {
		return Boolean
				.parseBoolean(appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_QUIT_WEB_DRIVER_AFTER_EXECUTION));
	}

	public static String getIterationLinuxOutLocation() {
		return MessageFormat.format(appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_ITERATION_LINUX_OUT_LOCATION),
				getIterationWorkspace());
	}

	public static String getIterationWindowsOutLocation() {
		return MessageFormat.format(appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_ITERATION_WINDOWS_OUT_LOCATION),
				getIterationWorkspace());
	}

	public static String getIterationWindowsOutLocationForDisplay() {
		String originalLocation = MessageFormat.format(
				appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_ITERATION_WINDOWS_OUT_LOCATION),
				getIterationWorkspace());
		originalLocation = originalLocation.replace("\\\\\\", "\\").replace("\\\\", "\\").substring(1);
		return WikiAutomatorUtils.replaceLast(originalLocation, "\\", "");
	}

	public static String getIterationLinuxOutLocationForDisplay() {
		String originalLocation = MessageFormat.format(
				appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_ITERATION_LINUX_OUT_LOCATION),
				getIterationWorkspace());
		originalLocation = originalLocation.replace("\\\\\\", "\\").replace("\\\\", "\\").substring(1);
		return WikiAutomatorUtils.replaceLast(originalLocation, "\\", "");
	}

	public static String getIterationWikiDisplayDateFormat() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_ITERATION_WIKI_DISPLAY_DATE_FORMAT);
	}

	public static String getProjectLocation() {
		return System.getProperty("user.dir");
	}

	public static String getWebDriverLocation() {
		if (WIKI_AUTOMATOR_ENVIRONMENT_PROD
				.equals(localResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_ENVIRONMENT))) {
			return ExternalResourceLoader.readExternalWebDriverFile();
		} else {
			return getProjectLocation() + File.separator + getResourcesLocation() + File.separator + getWebDriverName();
		}
	}

	public static void main(final String[] args) {
		System.out.println(getIterationWindowsOutLocation());
		System.out.println(getIterationLinuxOutLocation());
		System.out.println(getIterationWindowsOutLocationForDisplay());
		System.out.println(getIterationLinuxOutLocationForDisplay());
	}

}