package com.automate.wiki;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class FirstTest {

	public static void main(final String[] args) {
		// System Property for IEDriver
		System.setProperty("webdriver.ie.driver",
				"C:\\Users\\Arun Kumar S A\\Downloads\\IEDriverServer_x64_3.150.1\\IEDriverServer.exe");

		// Instantiate a IEDriver class.
		final WebDriver driver = new InternetExplorerDriver();

		// Launch Website
		driver.navigate().to("http://www.google.com/");

		// Maximize the browser
		driver.manage().window().maximize();

		// Click on the search text box and send value
		driver.findElement(By.xpath("//input[@title='Search']")).sendKeys("test");

		// Click on the search button
		driver.findElement(By.name("btnK")).click();
	}

}
