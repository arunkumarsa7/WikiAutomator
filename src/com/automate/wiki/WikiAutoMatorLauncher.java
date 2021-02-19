package com.automate.wiki;

import java.util.Scanner;

import com.automate.wiki.service.WikiModifier;
import com.automate.wiki.service.WikiSummaryReader;

public class WikiAutoMatorLauncher {

	public static void main(final String[] args) {
		try (final Scanner sc = new Scanner(System.in);) {
			do {
				System.out.println("\n ***************************************");
				System.out.println("| Please enter your choice!\t\t|");
				System.out.println(" ***************************************");
				System.out.println("| Show Entiwikler News Summary\t = 1\t|");
				System.out.println("| Update Entwikler News\t\t = 2\t|");
				System.out.println("| Exit\t\t\t\t = 3\t|");
				System.out.println(" ***************************************");
				try {
					final int userInput = Integer.parseInt(sc.nextLine());
					if (userInput == 1) {
						final WikiSummaryReader summaryReader = new WikiSummaryReader();
						summaryReader.readWikiSummary();
					} else if (userInput == 2) {
						final WikiModifier wikiModifier = new WikiModifier();
						wikiModifier.modifyWikiEntry();
					} else if (userInput == 3) {
						break;
					} else {
						throw new NumberFormatException();
					}
				} catch (final NumberFormatException ne) {
					System.out.println("Invalid input! please enter your choice again.");
				}
			} while (true);
			System.exit(999);
		}
	}

}
