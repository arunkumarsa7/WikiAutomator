package com.automate.wiki.test;

import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

public class WikiModifierTest {

	WebDriver webDriver;

	JavascriptExecutor javascriptExecutor;

	@Before
	public void setUp() {
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

	@Test
	public void modifyWikiEntry() {
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
	}

	@After
	public void tearDown() {
		if (ConfigReader.isQuitWebDriverAfterExecution()) {
			webDriver.quit();
		}
	}

}
