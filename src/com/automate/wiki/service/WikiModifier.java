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
			System.out.println("Calling summary before edit");
			final WikiSummaryReader summaryReader = new WikiSummaryReader();
			summaryReader.readWikiSummary(false, false);
			System.out.println("Finding edit");
			WebDriverVault.navigateAndMaximize(ConfigReader.getSourceUrl());
			final WebElement editElement = WebDriverVault.waitAndLoadWebElement(By.xpath("//a[@id='editPageLink']"));
			editElement.click();
			System.out.println("Edit clicked");
			final WebDriver webDriver = WebDriverVault.getWebDriver();
			System.out.println("Switching to iframe");
			WebDriverVault.switchToFrame(webDriver.findElement(By.id("wysiwygTextarea_ifr")));
			System.out.println("Switched");
			final WebElement entryElement = WebDriverVault
					.waitAndLoadWebElement(By.xpath(ConfigReader.getEntryElementXPath()));
			System.out.println("Entry element found");
			WebDriverVault.getJavascriptExecutor().executeScript(WikiAutomatorHelper.generateLatestWikiEntryForEdit(),
					entryElement);
			System.out.println("seems ok till now! Java script executed...");
			WebDriverVault.switchToDefault();
			System.out.println("Able to see me?");
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
