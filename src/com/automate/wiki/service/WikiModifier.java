package com.automate.wiki.service;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.automate.wiki.helper.ConfigReader;
import com.automate.wiki.helper.WebDriverVault;
import com.automate.wiki.helper.WikiAutomatorHelper;

public class WikiModifier {

	public void modifyWikiEntry() {
		try {
			final WebDriver webDriver = WebDriverVault.getWebDriver();
			webDriver.navigate().to(ConfigReader.getSourceUrl());
			webDriver.manage().window().maximize();
			WebDriverVault.waitAndLoadWebElement(By.xpath(ConfigReader.getParentElementXPath()));

			final WebElement editElement = webDriver.findElement(By.xpath("//a[@id='editPageLink']"));
			editElement.click();
			WebDriverVault.switchToFrame(webDriver.findElement(By.id("wysiwygTextarea_ifr")));

			final WebElement searchDiv = WebDriverVault
					.waitAndLoadWebElement(By.xpath(ConfigReader.getEntryElementXPath()));
			WebDriverVault.getJavascriptExecutor().executeScript(WikiAutomatorHelper.generateLatestWikiEntryForEdit(),
					searchDiv);
			if (ConfigReader.isNotifyEntwicklerNewsWatchers()) {
				final WebElement notifyWatchersElement = webDriver
						.findElement(By.xpath("//input[@id='notifyWatchers']"));
				if (!notifyWatchersElement.isSelected()) {
					notifyWatchersElement.click();
				}
			}
			if (ConfigReader.isPreviewEntwicklerNews()) {
				final WebElement ellipsisButtonElement = webDriver
						.findElement(By.xpath("//button[@id='rte-button-ellipsis']"));
				ellipsisButtonElement.click();
				final WebElement previewButtonElement = webDriver
						.findElement(By.xpath("//button[@id='rte-button-preview']"));
				previewButtonElement.click();
			}
			if (ConfigReader.isPublishEntwicklerNews()) {
				final WebElement publishButtonElement = webDriver
						.findElement(By.xpath("//button[@id='rte-button-publish']"));
				publishButtonElement.click();
			}
			WebDriverVault.switchToDefault();
		} catch (final NoSuchElementException ne) {
			System.out.println(ne.getMessage());
		} catch (final WebDriverException we) {
			System.err.println(we.getMessage());
		}
	}

}
