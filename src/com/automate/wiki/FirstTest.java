package com.automate.wiki;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FirstTest {

	WebDriver edgeDriver;

	@Before
	public void setUp() {
		System.setProperty("webdriver.edge.driver",
				"C:\\Users\\Arun Kumar S A\\Downloads\\edgedriver_win64_1\\msedgedriver.exe");
		edgeDriver = new EdgeDriver();
	}

	@Test
	public void headingText() {
		edgeDriver.navigate().to("https://phptravels.com/demo/");
		edgeDriver.manage().window().maximize();
		final String expectedHeading = "APPLICATION TEST DRIVE";
		final WebElement webElement = edgeDriver.findElement(By.xpath("//h2[@id='header-title']"));
		final WebDriverWait wait = new WebDriverWait(edgeDriver, 200);
		wait.until(ExpectedConditions.visibilityOf(webElement));
		final String heading = webElement.getText();
		if (expectedHeading.equalsIgnoreCase(heading)) {
			System.out.println("The expected heading is same as actual heading ---> " + heading);
		} else {
			System.out.println("The expected heading doesn't match the actual heading ---> " + heading);
		}
	}

	@After
	public void tearDown() {
		edgeDriver.quit();
	}

}
