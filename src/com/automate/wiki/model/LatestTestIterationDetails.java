package com.automate.wiki.model;

import com.automate.wiki.service.WikiSummaryReader;

public class LatestTestIterationDetails {

	public static TestIterationDetails testIterationDetails;

	private LatestTestIterationDetails() {

	}

	public static TestIterationDetails getLatestTestIterationDetails(final boolean isPrintWikiSummary) {
		if (testIterationDetails == null) {
			final WikiSummaryReader wikiSummaryReader = new WikiSummaryReader();
			wikiSummaryReader.readWikiSummary(isPrintWikiSummary);
		}
		return testIterationDetails;
	}

}
