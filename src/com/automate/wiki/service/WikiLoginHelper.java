package com.automate.wiki.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.automate.wiki.helper.ConfigReader;
import com.automate.wiki.helper.WebDriverVault;
import com.automate.wiki.model.LoggedInUserDetails;

public class WikiLoginHelper {

	public void loginToWiki() {
		try {
			final WebDriver webDriver = WebDriverVault.getWebDriver();
			webDriver.navigate().to(ConfigReader.getSourceUrl());
			webDriver.manage().window().maximize();
			final WebElement loginElement = WebDriverVault
					.waitAndLoadWebElement(By.xpath("//form[./h2[contains(text(), \"Log in\")]]"));
			if (loginElement != null) {
				final WebElement username = webDriver.findElement(By.xpath("//input[@id='os_username']"));
				final WebElement password = webDriver.findElement(By.xpath("//input[@id='os_password']"));
				username.clear();
				password.clear();
				username.sendKeys(ConfigReader.getWikiUsername());
				password.sendKeys(ConfigReader.getWikiPassword());
				webDriver.findElement(By.xpath("//input[@id='loginButton']")).click();

				webDriver.navigate().to(ConfigReader.getSourceUrl());
				WebDriverVault.waitAndLoadWebElement(By.xpath(ConfigReader.getParentElementXPath()));

				final WebElement loggedInUserName = webDriver.findElement(By.xpath("//a[@id='user-menu-link']"));
				LoggedInUserDetails.loggedInUsername = loggedInUserName.getAttribute("title");
			}
		} catch (final WebDriverException e) {
			System.err.println(e.getMessage());
		}
	}

}
