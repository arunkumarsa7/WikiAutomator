package com.automate.wiki.util;

import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_COFIG_FILE_NAME;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_ENVIRONMENT_PROD;
import static com.automate.wiki.constant.WikiAutomatorConstants.WIKI_AUTOMATOR_PROPERTY_ENVIRONMENT;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.io.FilenameUtils;

public class ConfigLoader {

	private static ResourceBundle appConfiguration;

	private static ResourceBundle localConfiguration;

	private static String environment;

	private ConfigLoader() {

	}

	static {
		localConfiguration = ResourceBundle.getBundle(FilenameUtils.removeExtension(WIKI_AUTOMATOR_COFIG_FILE_NAME));
		environment = localConfiguration.getString(WIKI_AUTOMATOR_PROPERTY_ENVIRONMENT);
	}

	private static void readExternalSystemConfigFile() {
		try (FileInputStream fisConfig = new FileInputStream(ExternalResourceLoader.readExternalConfigFileLocation())) {
			appConfiguration = new PropertyResourceBundle(fisConfig);
		} catch (final FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (final IOException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void loadSystemConfiguration() {
		if (WIKI_AUTOMATOR_ENVIRONMENT_PROD.equals(environment)) {
			readExternalSystemConfigFile();
		} else {
			readDefaultSystemConfigFile();
		}
	}

	private static void readDefaultSystemConfigFile() {
		if (appConfiguration == null) {
			appConfiguration = localConfiguration != null ? localConfiguration
					: ResourceBundle.getBundle(FilenameUtils.removeExtension(WIKI_AUTOMATOR_COFIG_FILE_NAME));
		}
	}

	public static ResourceBundle getAppConfiguration() {
		if (appConfiguration == null) {
			loadSystemConfiguration();
		}
		return appConfiguration;
	}

	public static ResourceBundle getLocalConfiguration() {
		if (localConfiguration == null) {
			localConfiguration = ResourceBundle
					.getBundle(FilenameUtils.removeExtension(WIKI_AUTOMATOR_COFIG_FILE_NAME));
		}
		return localConfiguration;
	}

}