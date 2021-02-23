package com.automate.wiki.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automate.wiki.helper.ConfigReader;
import com.automate.wiki.helper.WebDriverVault;
import com.automate.wiki.helper.WikiAutomatorHelper;
import com.automate.wiki.model.TestIterationDetails;
import com.automate.wiki.util.WikiAutomatorUtils;

public class WikiSummaryReader {

	WebDriver webDriver;

	public void readWikiSummary(final boolean isPrintWikiSummary) {
		try {
			webDriver = WebDriverVault.getWebDriver();
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
				Collections.sort(testIterationDetails);
				WikiAutomatorHelper.generateSummaryReport(testIterationDetails, isPrintWikiSummary);
				if (isPrintWikiSummary && ConfigReader.isgGenerateDetailedSummaryReport()) {
					readDetailedWikiSummary(testIterationDetails.get(0));
				}
			}
		} catch (final WebDriverException e) {
			System.err.println(e.getMessage());
		}
	}

	private void readDetailedWikiSummary(final TestIterationDetails testIterationDetails) {
		final List<TestIterationDetails> childTestIterationDetails = new ArrayList<>();
		int latestIterationYear = Integer
				.parseInt(new SimpleDateFormat("yyyy").format(testIterationDetails.getTestIterationDate()));
		for (int i = ConfigReader.getDetailedSummaryReportUptoYear(); i > 0; i--) {
			try {
				final WebElement childElement = webDriver.findElement(By.xpath("//span[./a[contains(text(), \"News "
						+ (--latestIterationYear) + "\")]]//preceding-sibling::a"));
				if (childElement != null) {
					childElement.click();
					for (int currentMonth = 1; currentMonth <= 12; currentMonth++) {
						final String elementFinder = latestIterationYear + "."
								+ new DecimalFormat("00").format(currentMonth);
						try {
							final WebElement entwicklerElement = webDriver.findElement(
									By.xpath("//span[./a[contains(text(), \"News " + elementFinder + "\")]]"));
							if (entwicklerElement != null) {
								entwicklerElement.click();
								final List<WebElement> testIterationWebElements = webDriver
										.findElements(By.xpath(ConfigReader.getIterationElementXPath()));
								if (testIterationWebElements != null && !testIterationWebElements.isEmpty()) {
									childTestIterationDetails
											.addAll(populateTestIterationDetails(testIterationWebElements));
								}
							}
						} catch (final NoSuchElementException ne) {
							System.out
									.println("No entry found for '" + elementFinder + "', searching for next element.");
						}
					}
				}
			} catch (final NoSuchElementException ne) {
				System.out.println("No entry found for '" + latestIterationYear + "', searching for next element.");
			}
		}
		if (!childTestIterationDetails.isEmpty()) {
			Collections.sort(childTestIterationDetails);
			WikiAutomatorHelper.generateDetailedSummaryReport(childTestIterationDetails);
		}
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

}
