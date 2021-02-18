package com.automate.wiki.test;

import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
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
		webDriver.navigate().to(ConfigReader.getSourceUrl());
		webDriver.manage().window().maximize();
		final WebElement webElement = webDriver.findElement(By.xpath("//div[@id='main-header-placeholder']"));
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(200));
		wait.until(ExpectedConditions.visibilityOf(webElement));
		final WebElement searchDiv = webDriver.findElement(By.xpath("(//div[@class='table-wrap'])[3]"));
		javascriptExecutor.executeScript(WikiAutomatorUtil.generateLatestWikiEntry(), searchDiv);
	}

	@After
	public void tearDown() {
		webDriver.quit();
	}

}
