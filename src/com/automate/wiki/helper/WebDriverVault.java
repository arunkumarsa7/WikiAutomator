package com.automate.wiki.helper;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverVault {

	private static WebDriver webDriver;

	private static WebDriverWait wait;

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
		wait = new WebDriverWait(webDriver, Duration.ofSeconds(ConfigReader.getWebDriverWaitTill()));
	}

	private static void tearDown() {
		if (ConfigReader.isQuitWebDriverAfterExecution()) {
			webDriver.quit();
		}
	}

	public static void switchToFrame(final WebElement frameElement) {
		getWebDriver().switchTo().frame(frameElement);
	}

	public static void switchToDefault() {
		getWebDriver().switchTo().defaultContent();
	}

	public static WebDriver getWebDriver() {
		if (webDriver == null) {
			setUp();
		}
		return webDriver;
	}

	public static WebDriverWait getWebDriverWait() {
		if (wait == null) {
			setUp();
		}
		return wait;
	}

	public static void disposeWebDriver() {
		if (webDriver == null) {
			tearDown();
		}
	}

	public static WebElement waitAndLoadWebElement(final By elementBy) {
		return getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(elementBy));
	}

	public static JavascriptExecutor getJavascriptExecutor() {
		return (JavascriptExecutor) getWebDriver();
	}

	public static void navigateAndMaximize(final String sourceUrl) {
		final WebDriver webDriver = getWebDriver();
		webDriver.navigate().to(sourceUrl);
		webDriver.manage().window().maximize();
	}

}
