package com.automate.wiki.model;

import org.junit.runner.JUnitCore;

import com.automate.wiki.service.WikiSummaryReader;

public class LatestTestIterationDetails {

	public static TestIterationDetails testIterationDetails;

	private LatestTestIterationDetails() {

	}

	public static TestIterationDetails getLatestTestIterationDetails() {
		if (testIterationDetails == null) {
			final JUnitCore junit = new JUnitCore();
			junit.run(WikiSummaryReader.class);
		}
		return testIterationDetails;
	}

}
