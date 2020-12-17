package com.automate.wiki;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

public class FirstTest {

	public static void main(final String[] args) {
		// System Property for IEDriver
		System.setProperty("webdriver.edge.driver",
				"C:\\Users\\Arun Kumar S A\\Downloads\\edgedriver_win64\\msedgedriver.exe");

		// Instantiate a IEDriver class.
		final WebDriver edgeDriver = new EdgeDriver();

		// Launch Website
		edgeDriver.navigate().to("http://www.google.com/");

		// Maximize the browser
		edgeDriver.manage().window().maximize();

		// Click on the search text box and send value
		final WebElement searchField = edgeDriver.findElement(By.xpath("//input[@title='Search']"));
		searchField.sendKeys("test");

		final JavascriptExecutor js = (JavascriptExecutor) edgeDriver;
		// Click on the search button
		// driver.findElement(By.name("btnK")).click();
		js.executeScript("arguments[0].click();", searchField);
	}

}
