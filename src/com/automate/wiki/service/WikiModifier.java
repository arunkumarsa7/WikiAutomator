package com.automate.wiki.service;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automate.wiki.helper.ConfigReader;
import com.automate.wiki.util.WikiAutomatorUtil;

public class WikiModifier {

	WebDriver webDriver;

	JavascriptExecutor javascriptExecutor;

	private void setUp() {
		final EdgeOptions edgeOptions = new EdgeOptions();
		if (ConfigReader.isHeadlessMode()) {
			edgeOptions.addArguments("headless");
		}
		if (ConfigReader.isDisableGPU()) {
			edgeOptions.addArguments("disable-gpu");
		}
		System.setProperty(ConfigReader.getWebDriver(), ConfigReader.getWebDriverLocation());
		webDriver = new EdgeDriver(edgeOptions);
		javascriptExecutor = (JavascriptExecutor) webDriver;
	}

	public void modifyWikiEntry() {
		setUp();
		try {
			webDriver.navigate().to(ConfigReader.getSourceUrl());
			webDriver.manage().window().maximize();
			final WebElement webElement = webDriver.findElement(By.xpath(ConfigReader.getParentElementXPath()));
			final WebDriverWait wait = new WebDriverWait(webDriver,
					Duration.ofSeconds(ConfigReader.getWebDriverWaitTill()));
			wait.until(ExpectedConditions.visibilityOf(webElement));
			final WebElement searchDiv = webDriver.findElement(By.xpath(ConfigReader.getEntryElementXPath()));
			javascriptExecutor.executeScript(WikiAutomatorUtil.generateLatestWikiEntry(), searchDiv);
		} catch (final WebDriverException e) {
			System.err.println(e.getMessage());
		}
		tearDown();
	}

	private void tearDown() {
		if (ConfigReader.isQuitWebDriverAfterExecution()) {
			webDriver.quit();
		}
	}

}
