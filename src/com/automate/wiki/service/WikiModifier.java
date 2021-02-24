package com.automate.wiki.service;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.automate.wiki.helper.ConfigReader;
import com.automate.wiki.helper.WebDriverVault;

public class WikiModifier {

	public void modifyWikiEntry() {
		try {
			final WikiSummaryReader summaryReader = new WikiSummaryReader();
			summaryReader.readWikiSummary(false, false);

			WebDriverVault.navigateAndMaximize(ConfigReader.getSourceUrl());
			final WebElement editElement = WebDriverVault.waitAndLoadWebElement(By.xpath("//a[@id='editPageLink']"));
			editElement.click();

			final WebDriver webDriver = WebDriverVault.getWebDriver();
			WebDriverVault.switchToFrame(webDriver.findElement(By.id("wysiwygTextarea_ifr")));
//			final WebElement entryElement = WebDriverVault
//					.waitAndLoadWebElement(By.xpath(ConfigReader.getEntryElementXPath()));
//			WebDriverVault.getJavascriptExecutor().executeScript(WikiAutomatorHelper.generateLatestWikiEntryForEdit(),
//					entryElement);
//			System.out.println("seems ok till now!");
//			WebDriverVault.switchToDefault();
//			System.out.println("Able to see me?");
//			if (ConfigReader.isNotifyEntwicklerNewsWatchers()) {
//				final WebElement notifyWatchersElement = webDriver
//						.findElement(By.xpath("//input[@id='notifyWatchers']"));
//				if (!notifyWatchersElement.isSelected()) {
//					notifyWatchersElement.click();
//				}
//			}
//			if (ConfigReader.isPreviewEntwicklerNews()) {
//				final WebElement ellipsisButtonElement = webDriver
//						.findElement(By.xpath("//button[@id='rte-button-ellipsis']"));
//				ellipsisButtonElement.click();
//				final WebElement previewButtonElement = webDriver
//						.findElement(By.xpath("//button[@id='rte-button-preview']"));
//				previewButtonElement.click();
//			}
//			if (ConfigReader.isPublishEntwicklerNews()) {
//				final WebElement publishButtonElement = webDriver
//						.findElement(By.xpath("//button[@id='rte-button-publish']"));
//				publishButtonElement.click();
//			}
		} catch (final NoSuchElementException ne) {
			System.out.println(ne.getMessage());
		} catch (final WebDriverException we) {
			System.err.println(we.getMessage());
		}
	}

}
