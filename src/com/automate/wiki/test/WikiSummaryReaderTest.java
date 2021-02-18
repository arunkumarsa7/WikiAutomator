package com.automate.wiki.test;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automate.wiki.model.TestIterationDetails;
import com.automate.wiki.util.CalendarUtils;
import com.automate.wiki.util.WikiAutomatorUtil;

public class WikiSummaryReaderTest {

	private static final String WEB_DRIVER = "webdriver.edge.driver";

	private static final String WEB_DRIVER_LOCATION = "C:\\Users\\Arun Kumar S A\\Downloads\\edgedriver_win64_1\\msedgedriver.exe";

	private static final String SOURCE_URL = "http://localhost:8080/test/";

	WebDriver webDriver;

	@Before
	public void setUp() {
		final EdgeOptions edgeOptions = new EdgeOptions();
		edgeOptions.addArguments("headless", "disable-gpu");
		System.setProperty(WEB_DRIVER, WEB_DRIVER_LOCATION);
		webDriver = new EdgeDriver(edgeOptions);
	}

	@Test
	public void readWikiSummary() {
		webDriver.navigate().to(SOURCE_URL);
		webDriver.manage().window().maximize();
		final WebElement webElement = webDriver.findElement(By.xpath("//div[@id='main-header-placeholder']"));
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(200));
		wait.until(ExpectedConditions.visibilityOf(webElement));
		final String heading = webElement.getText();
		System.out.println(heading + " loaded...");
		System.out.println("Searching Entwicklernews for Test iteration details");
		final List<WebElement> webElements = webDriver
				.findElements(By.xpath("//h4[contains(@id, 'End-of-Test-Iteration')]"));
		System.out.println("Found " + webElements.size() + " entries");
		if (!webElements.isEmpty()) {
			final List<TestIterationDetails> testIterationDetails = populateTestIterationDetails(webElements);
			WikiAutomatorUtil.generateSummaryReport(testIterationDetails);
		}
	}

	private List<TestIterationDetails> populateTestIterationDetails(final List<WebElement> webElements) {
		return webElements.stream().map(tempWebElement -> {
			final Integer testIterationNumber = Integer.parseInt(tempWebElement.getAttribute("id").split("-")[5]);
			final Date testIterationDate = CalendarUtils.getTestIterationDate(
					tempWebElement.findElement(By.xpath("following-sibling::h3[1]")).getText(),
					TimeZone.getTimeZone("CET"), Calendar.getInstance().getTimeZone());
			final String testIterationDescription = tempWebElement.findElement(By.xpath("following-sibling::p[1]"))
					.getText();
			final String wikiAuthor = WikiAutomatorUtil
					.getWikiAuthor(tempWebElement.findElement(By.xpath("following-sibling::p[2]")).getText());
			return new TestIterationDetails(testIterationNumber, testIterationDate, testIterationDescription,
					wikiAuthor);
		}).collect(Collectors.toList());
	}

	@After
	public void tearDown() {
		webDriver.quit();
	}

}
