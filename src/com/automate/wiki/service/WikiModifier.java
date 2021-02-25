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
			System.out.println("Updating Entwicklernews");
			final WikiSummaryReader summaryReader = new WikiSummaryReader();
			summaryReader.readWikiSummary(false, false);
			WebDriverVault.navigateAndMaximize(ConfigReader.getSourceUrl());
			final WebElement editElement = WebDriverVault.waitAndLoadWebElement(By.xpath("//a[@id='editPageLink']"));
			editElement.click();
			final WebDriver webDriver = WebDriverVault.getWebDriver();
			WebDriverVault.switchToFrame(webDriver.findElement(By.id("wysiwygTextarea_ifr")));
			final WebElement entryElement = WebDriverVault
					.waitAndLoadWebElement(By.xpath(ConfigReader.getEntryElementXPath()));
			WebDriverVault.getJavascriptExecutor().executeScript(WikiAutomatorHelper.generateLatestWikiEntryForEdit(),
					entryElement);
			WebDriverVault.switchToDefault();
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
						.findElement(By.xpath("//a[@id='rte-button-preview']"));
				previewButtonElement.click();
			}
			if (ConfigReader.isPublishEntwicklerNews()) {
				final WebElement publishButtonElement = webDriver
						.findElement(By.xpath("//button[@id='rte-button-publish']"));
				publishButtonElement.click();
			}
			System.out.println("Entwicklernews updated successfully");
		} catch (final NoSuchElementException ne) {
			System.out.println(ne.getMessage());
		} catch (final WebDriverException we) {
			System.err.println(we.getMessage());
		}
	}

}
