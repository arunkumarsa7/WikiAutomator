/**
 *
 */
package com.automate.wiki.util;

import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_CONFIG_LOCATION;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_JAR_LOCATION;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_RESOURCES_LOCATION;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import com.automate.wiki.helper.ConfigReader;

public class ExternalResourceLoader {

	private ExternalResourceLoader() {

	}

	private static String jarFileLocation;

	private static String externalConfigLocation;

	private static String externalResourceLocation;

	static {
		jarFileLocation = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath()).getAbsolutePath()
				.replace("%20", " ");
	}

	private static void loadExternalConfigLocation() {
		externalConfigLocation = jarFileLocation.replace(
				File.separator + ConfigLoader.getLocalConfiguration().getString(WIKI_AUTOMATOR_PROPERTY_JAR_LOCATION),
				File.separator
						+ ConfigLoader.getLocalConfiguration().getString(WIKI_AUTOMATOR_PROPERTY_CONFIG_LOCATION));
	}

	private static void loadExternalResourceLocation() {
		externalResourceLocation = jarFileLocation.replace(
				File.separator + ConfigLoader.getLocalConfiguration().getString(WIKI_AUTOMATOR_PROPERTY_JAR_LOCATION),
				File.separator
						+ ConfigLoader.getLocalConfiguration().getString(WIKI_AUTOMATOR_PROPERTY_RESOURCES_LOCATION));
	}

	private static String readExternalConfigLocation() {
		if (StringUtils.isEmpty(externalConfigLocation)) {
			loadExternalConfigLocation();
		}
		return externalConfigLocation;
	}

	private static String readExternalResourceLocation() {
		if (StringUtils.isEmpty(externalResourceLocation)) {
			loadExternalResourceLocation();
		}
		return externalResourceLocation;
	}

	public static String readExternalConfigFileLocation() {
		return readExternalConfigLocation() + File.separator + ConfigReader.getConfigFileName();
	}

	public static String readExternalWebDriverFile() {
		return readExternalResourceLocation() + File.separator + ConfigReader.getWebDriverName();
	}

}
