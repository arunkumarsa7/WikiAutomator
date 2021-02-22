package com.automate.wiki.service;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automate.wiki.helper.ConfigReader;
import com.automate.wiki.model.LoggedInUserDetails;

public class WikiLoginHelper {

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

	public void loginToWiki() {
		setUp();
		try {
			webDriver.navigate().to(ConfigReader.getSourceUrl());
			webDriver.manage().window().maximize();
			final WebElement loginElement = webDriver
					.findElement(By.xpath("//form[./h2[contains(text(), \"Log in\")]]"));
			final WebDriverWait wait = new WebDriverWait(webDriver,
					Duration.ofSeconds(ConfigReader.getWebDriverWaitTill()));
			wait.until(ExpectedConditions.visibilityOf(loginElement));
			if (loginElement != null) {
				System.out.println("Login element found...");
				final WebElement username = webDriver.findElement(By.xpath("//input[@id='os_username']"));
				final WebElement password = webDriver.findElement(By.xpath("//input[@id='os_password']"));
				username.clear();
				password.clear();
				username.sendKeys(ConfigReader.getWikiUsername());
				password.sendKeys(ConfigReader.getWikiPassword());
				webDriver.findElement(By.xpath("//input[@id='loginButton']")).click();

				webDriver.navigate().to(ConfigReader.getSourceUrl());
				final WebElement webElement = webDriver.findElement(By.xpath(ConfigReader.getParentElementXPath()));
				wait.until(ExpectedConditions.visibilityOf(webElement));

				final WebElement loggedInUserName = webDriver.findElement(By.xpath("//a[@id='user-menu-link']"));
				LoggedInUserDetails.loggedInUsername = loggedInUserName.getAttribute("title");
			}
		} catch (final WebDriverException e) {
			System.err.println(e.getMessage());
		}
		tearDown();
	}

	public void tearDown() {
		if (ConfigReader.isQuitWebDriverAfterExecution()) {
			webDriver.quit();
		}
	}

}
