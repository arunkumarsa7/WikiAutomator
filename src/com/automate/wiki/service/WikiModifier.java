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
			final WebElement editElement = WebDriverVault
					.waitAndLoadWebElement(By.xpath(ConfigReader.getEditPageEditButton()));
			editElement.click();
			final WebDriver webDriver = WebDriverVault.getWebDriver();
			WebDriverVault.switchToFrame(webDriver.findElement(By.id(ConfigReader.getEditPageIFrameId())));
			final WebElement entryElement = WebDriverVault
					.waitAndLoadWebElement(By.xpath(ConfigReader.getEntryElementXPath()));
			WebDriverVault.getJavascriptExecutor().executeScript(WikiAutomatorHelper.generateLatestWikiEntryForEdit(),
					entryElement);
			WebDriverVault.switchToDefault();
			final WebElement notifyWatchersElement = webDriver
					.findElement(By.xpath(ConfigReader.getEditPageNotifyWatchersCheckbox()));
			if ((ConfigReader.isNotifyEntwicklerNewsWatchers() && !notifyWatchersElement.isSelected())
					|| (!ConfigReader.isNotifyEntwicklerNewsWatchers() && notifyWatchersElement.isSelected())) {
				notifyWatchersElement.click();
			}
			if (ConfigReader.isPreviewEntwicklerNews()) {
				final WebElement ellipsisButtonElement = webDriver
						.findElement(By.xpath(ConfigReader.getEditPageEllipsisButton()));
				ellipsisButtonElement.click();
				final WebElement previewButtonElement = webDriver
						.findElement(By.xpath(ConfigReader.getEditPagePreviewButton()));
				previewButtonElement.click();
			}
			if (ConfigReader.isPublishEntwicklerNews()) {
				final WebElement publishButtonElement = webDriver
						.findElement(By.xpath(ConfigReader.getEditPagePublishButton()));
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
