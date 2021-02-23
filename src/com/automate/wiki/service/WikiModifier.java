package com.automate.wiki.service;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automate.wiki.helper.ConfigReader;
import com.automate.wiki.helper.WebDriverVault;
import com.automate.wiki.helper.WikiAutomatorHelper;

public class WikiModifier {

	WebDriver webDriver;

	JavascriptExecutor javascriptExecutor;

	public void modifyWikiEntry() {
		try {
			getWebDriverAndLoadPage();
			final WebElement editElement = webDriver.findElement(By.xpath("//a[@id='editPageLink']"));
			editElement.click();
			final WebElement searchDiv = webDriver.findElement(By.xpath(ConfigReader.getEntryElementXPath()));
			final WebDriverWait wait = WebDriverVault.getWebDriverWait();
			wait.until(ExpectedConditions.visibilityOf(searchDiv));

			javascriptExecutor.executeScript(WikiAutomatorHelper.generateLatestWikiEntryForEdit(), searchDiv);
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
			// Need to integrate checkbox on-off code changes and update button change
		} catch (final NoSuchElementException ne) {
			System.out.println(ne.getMessage());
		} catch (final WebDriverException we) {
			System.err.println(we.getMessage());
		}
	}

	private void getWebDriverAndLoadPage() {
		webDriver = WebDriverVault.getWebDriver();
		webDriver.navigate().to(ConfigReader.getSourceUrl());
		webDriver.manage().window().maximize();
		final WebElement webElement = webDriver.findElement(By.xpath(ConfigReader.getParentElementXPath()));
		final WebDriverWait wait = new WebDriverWait(webDriver,
				Duration.ofSeconds(ConfigReader.getWebDriverWaitTill()));
		wait.until(ExpectedConditions.visibilityOf(webElement));
		javascriptExecutor = (JavascriptExecutor) webDriver;
	}

}
