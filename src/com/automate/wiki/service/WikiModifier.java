package com.automate.wiki.service;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automate.wiki.helper.ConfigReader;
import com.automate.wiki.helper.WebDriverVault;
import com.automate.wiki.helper.WikiAutomatorHelper;

public class WikiModifier {

	public void modifyWikiEntry() {
		try {
			final WebDriver webDriver = WebDriverVault.getWebDriver();
			final JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
			webDriver.navigate().to(ConfigReader.getSourceUrl());
			webDriver.manage().window().maximize();
			final WebElement webElement = webDriver.findElement(By.xpath(ConfigReader.getParentElementXPath()));
			final WebDriverWait wait = new WebDriverWait(webDriver,
					Duration.ofSeconds(ConfigReader.getWebDriverWaitTill()));
			wait.until(ExpectedConditions.visibilityOf(webElement));
			final WebElement searchDiv = webDriver.findElement(By.xpath(ConfigReader.getEntryElementXPath()));
			javascriptExecutor.executeScript(WikiAutomatorHelper.generateLatestWikiEntryForEdit(), searchDiv);
			// Need to integrate checkbox on-off code changes and update button change
		} catch (final WebDriverException e) {
			System.err.println(e.getMessage());
		}
	}

}
