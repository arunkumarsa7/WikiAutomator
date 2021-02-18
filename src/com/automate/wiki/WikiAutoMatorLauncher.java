package com.automate.wiki;

import org.junit.runner.JUnitCore;

import com.automate.wiki.test.WikiSummaryReaderTest;

public class WikiAutoMatorLauncher {

	public static void main(final String[] args) {
		final JUnitCore junit = new JUnitCore();
		junit.run(WikiSummaryReaderTest.class);
	}

}
