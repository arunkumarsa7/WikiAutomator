package com.automate.wiki.helper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class WebDriverVault {

	private static WebDriver webDriver;

	private WebDriverVault() {

	}

	private static void setUp() {
		final EdgeOptions edgeOptions = new EdgeOptions();
		if (ConfigReader.isHeadlessMode()) {
			edgeOptions.addArguments("headless");
		}
		if (ConfigReader.isDisableGPU()) {
			edgeOptions.addArguments("disable-gpu");
		}
		System.setProperty(ConfigReader.getWebDriver(), ConfigReader.getWebDriverLocation());
		webDriver = new EdgeDriver(edgeOptions);
	}

	private static void tearDown() {
		if (ConfigReader.isQuitWebDriverAfterExecution()) {
			webDriver.quit();
		}
	}

	public static WebDriver getWebDriver() {
		if (webDriver == null) {
			setUp();
		}
		return webDriver;
	}

	public static void disposeWebDriver() {
		if (webDriver == null) {
			tearDown();
		}
	}

}
