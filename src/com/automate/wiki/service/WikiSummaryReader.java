package com.automate.wiki.service;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automate.wiki.helper.ConfigReader;
import com.automate.wiki.helper.WikiAutomatorHelper;
import com.automate.wiki.model.TestIterationDetails;
import com.automate.wiki.util.WikiAutomatorUtils;

public class WikiSummaryReader {

	private WebDriver webDriver;

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
	}

	public void readWikiSummary(final boolean isPrintWikiSummary) {
		setUp();
		try {
			webDriver.navigate().to(ConfigReader.getSourceUrl());
			webDriver.manage().window().maximize();
			final WebElement webElement = webDriver.findElement(By.xpath(ConfigReader.getParentElementXPath()));
			final WebDriverWait wait = new WebDriverWait(webDriver,
					Duration.ofSeconds(ConfigReader.getWebDriverWaitTill()));
			wait.until(ExpectedConditions.visibilityOf(webElement));
			if (isPrintWikiSummary) {
				System.out.println(webElement.getText() + " loaded...");
				System.out.println("Searching Entwicklernews for Test iteration details");
			}
			final List<WebElement> webElements = webDriver
					.findElements(By.xpath(ConfigReader.getIterationElementXPath()));
			if (isPrintWikiSummary) {
				System.out.println("Found " + webElements.size() + " entries");
			}
			if (!webElements.isEmpty()) {
				final List<TestIterationDetails> testIterationDetails = populateTestIterationDetails(webElements);
				WikiAutomatorHelper.generateSummaryReport(testIterationDetails, isPrintWikiSummary);
			}
		} catch (final WebDriverException e) {
			System.err.println(e.getMessage());
		}
		tearDown();
	}

	private List<TestIterationDetails> populateTestIterationDetails(final List<WebElement> webElements) {
		return webElements.stream().map(tempWebElement -> {
			final Integer testIterationNumber = Integer.parseInt(tempWebElement.getAttribute("id").split("-")[5]);
			final Date testIterationDate = WikiAutomatorUtils.getTestIterationDate(
					tempWebElement.findElement(By.xpath(ConfigReader.getIterationDateElementXPath())).getText(),
					TimeZone.getTimeZone(ConfigReader.getTargetTimezone()), Calendar.getInstance().getTimeZone());
			final String testIterationDescription = tempWebElement
					.findElement(By.xpath(ConfigReader.getIterationDescriptionElementXPath())).getText();
			final String wikiAuthor = WikiAutomatorHelper.getWikiAuthor(
					tempWebElement.findElement(By.xpath(ConfigReader.getIterationAuthorElementXPath())).getText());
			return new TestIterationDetails(testIterationNumber, testIterationDate, testIterationDescription,
					wikiAuthor);
		}).collect(Collectors.toList());
	}

	public void tearDown() {
		webDriver.quit();
	}

}
