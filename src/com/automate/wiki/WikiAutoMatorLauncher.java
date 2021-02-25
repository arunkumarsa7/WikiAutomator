package com.automate.wiki;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import com.automate.wiki.helper.ConfigReader;
import com.automate.wiki.helper.WebDriverVault;
import com.automate.wiki.model.LoggedInUserDetails;
import com.automate.wiki.service.WikiModifier;
import com.automate.wiki.service.WikiModifierVerifier;
import com.automate.wiki.service.WikiSummaryReader;

public class WikiAutoMatorLauncher {

	public static void main(final String[] args) {
		System.out.println("Loading...");
		try (final Scanner sc = new Scanner(System.in);) {
			System.out.println("\nWelcome " + (StringUtils.isNotBlank(LoggedInUserDetails.getLoggedInUsername())
					? LoggedInUserDetails.getLoggedInUsername()
					: "Guest") + "!");
			do {
				System.out.println("\n ****************************************");
				System.out.println(" *\tPlease enter your choice!\t*");
				System.out.println(" ****************************************");
				System.out.println(" * 1 = Show Entiwicklernews summary\t*");
				System.out.println(" * 2 = Verify new Entwicklernews entry\t*");
				System.out.println(" * 3 = Update Entwicklernews\t\t*");
				System.out.println(" * 4 = Exit\t\t\t\t*");
				System.out.println(" ****************************************");
				try {
					final int userInput = Integer.parseInt(sc.nextLine());
					if (userInput == 1) {
						final WikiSummaryReader summaryReader = new WikiSummaryReader();
						summaryReader.readWikiSummary(ConfigReader.isgGenerateDetailedSummaryReport(), true);
					} else if (userInput == 2) {
						final WikiModifierVerifier wikiModifierVerifier = new WikiModifierVerifier();
						wikiModifierVerifier.verifyWikiEntry();
					} else if (userInput == 3) {
						System.out.println(" ***********************************************************");
						System.out.println(" * WARNING : Have you verified the details using option 2? *");
						System.out.println(" ***********************************************************");
						System.out.println(" * Are you sure you want update Entwicklernews? \t   *");
						System.out.println(" * 1 = Yes \t\t\t\t\t\t   *");
						System.out.println(" * 2 = No \t\t\t\t\t\t   *");
						System.out.println(" ***********************************************************");
						final int confirmUserInput = Integer.parseInt(sc.nextLine());
						if (confirmUserInput == 1) {
							final WikiModifier wikiModifier = new WikiModifier();
							wikiModifier.modifyWikiEntry();
						} else if (confirmUserInput != 2) {
							throw new NumberFormatException();
						}

					} else if (userInput == 4) {
						break;
					} else {
						throw new NumberFormatException();
					}
				} catch (final NumberFormatException ne) {
					System.out.println("ERROR : Invalid input! please enter a correct option.");
				}
			} while (true);
			WebDriverVault.disposeWebDriver();
			System.exit(999);
		}
	}

}
