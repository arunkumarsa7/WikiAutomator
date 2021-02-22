package com.automate.wiki;

import java.util.Scanner;

import com.automate.wiki.helper.WebDriverVault;
import com.automate.wiki.model.LoggedInUserDetails;
import com.automate.wiki.service.WikiModifier;
import com.automate.wiki.service.WikiModifierVerifier;
import com.automate.wiki.service.WikiSummaryReader;

public class WikiAutoMatorLauncher {

	public static void main(final String[] args) {
		try (final Scanner sc = new Scanner(System.in);) {
			System.out.println("\nWelcome " + LoggedInUserDetails.getLoggedInUsername() + "!");
			do {
				System.out.println(" ***************************************");
				System.out.println("*\tPlease enter your choice!\t*");
				System.out.println(" ***************************************");
				System.out.println("* 1 = Show Entiwikler news summary\t*");
				System.out.println("* 2 = Verify new Entwikler news entry\t*");
				System.out.println("* 3 = Update Entwikler news\t\t*");
				System.out.println("* 4 = Exit\t\t\t\t*");
				System.out.println(" ***************************************");
				try {
					final int userInput = Integer.parseInt(sc.nextLine());
					if (userInput == 1) {
						final WikiSummaryReader summaryReader = new WikiSummaryReader();
						summaryReader.readWikiSummary(true);
					} else if (userInput == 2) {
						final WikiModifierVerifier wikiModifierVerifier = new WikiModifierVerifier();
						wikiModifierVerifier.verifyWikiEntry();
					} else if (userInput == 3) {
						System.out.println("WARNING : Have you verified the details using option 2.");
						System.out.println("Are you sure you want update Entwikler news ?");
						System.out.println("1 = Yes");
						System.out.println("2 = No");
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
