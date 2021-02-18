package com.automate.wiki.helper;

import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_ENVIRONMENT_PROD;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_CONFIG_FILE_NAME;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_DESTINATION_FILE_LOCATION;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_DISABLE_GPU;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_ENVIRONMENT;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_HEADLESS_MODE;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_ITERATION_TARGET_DATE_FORMAT;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_NEXT_ITERATION_DEFAULT_COMPLETION_TIME;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_RESOURCES_FILE_NAME;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_RESOURCES_LOCATION;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_SOURCE_FILE_NAME;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_SOURCE_URL;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_TARGET_DAY;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_WEB_DRIVER;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_WEB_DRIVER_NAME;

import java.io.File;
import java.util.ResourceBundle;

import com.automate.wiki.util.ConfigLoader;
import com.automate.wiki.util.ExternalResourceLoader;

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

	public static String getSourceFileName() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_SOURCE_FILE_NAME);
	}

	public static String getResourcesFileName() {
		return appResourceBundle.getString(WIKI_AUTOMATOR_PROPERTY_RESOURCES_FILE_NAME);
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
		System.out.println(getWebDriverLocation());
		System.out.println(getIterationTargetDateFormat());
	}

}