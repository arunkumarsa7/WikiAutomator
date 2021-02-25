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
					.waitAndLoadWebElement(By.xpath(ConfigReader.getLoginPageVerifyField()));
			if (loginElement != null) {
				final WebElement username = webDriver.findElement(By.xpath(ConfigReader.getLoginPageUsernameField()));
				final WebElement password = webDriver.findElement(By.xpath(ConfigReader.getLoginPagePasswordField()));
				username.clear();
				password.clear();
				username.sendKeys(ConfigReader.getWikiUsername());
				password.sendKeys(ConfigReader.getWikiPassword());
				webDriver.findElement(By.xpath(ConfigReader.getLoginPageLoginButton())).click();

				webDriver.navigate().to(ConfigReader.getSourceUrl());
				WebDriverVault.waitAndLoadWebElement(By.xpath(ConfigReader.getParentElementXPath()));

				final WebElement loggedInUserName = webDriver.findElement(By.xpath(ConfigReader.getMainPageUsername()));
				LoggedInUserDetails.loggedInUsername = loggedInUserName
						.getAttribute(ConfigReader.getMainPageUsernameAttribute());
			}
		} catch (final WebDriverException e) {
			System.err.println(e.getMessage());
		}
	}

}
