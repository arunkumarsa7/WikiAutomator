package com.automate.wiki.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;

import com.automate.wiki.model.TestIterationDetails;

public class WikiAutomatorUtil {

	private static final String ITERATION_DATE_FORMAT = "dd.MM.yyyy HH:mm";

	private static final String ITERATION_TARGET_DATE_FORMAT = "dd MMM yyyy EEEE hh:mmaa";

	private WikiAutomatorUtil() {

	}

	public static Date getTestIterationDate(final String testIterationDateText) {
		final Calendar calendar = Calendar.getInstance();
		try {
			final Date iterationDate = new SimpleDateFormat(ITERATION_DATE_FORMAT).parse(StringUtils.normalizeSpace(
					StringUtils.remove(StringUtils.removeIgnoreCase(testIterationDateText, "Uhr"), "-")));
			calendar.setTime(iterationDate);
			final TimeZone fromTimeZone = TimeZone.getTimeZone("CET");
			final TimeZone toTimeZone = calendar.getTimeZone();
			calendar.setTimeZone(fromTimeZone);
			calendar.add(Calendar.MILLISECOND, fromTimeZone.getRawOffset() * -1);
			if (fromTimeZone.inDaylightTime(calendar.getTime())) {
				calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getDSTSavings() * -1);
			}
			calendar.add(Calendar.MILLISECOND, toTimeZone.getRawOffset());
			if (toTimeZone.inDaylightTime(calendar.getTime())) {
				calendar.add(Calendar.MILLISECOND, toTimeZone.getDSTSavings());
			}
		} catch (final ParseException e) {
			System.err.println(e.getMessage());
		}
		return calendar.getTime();
	}

	public static void generateSummaryReport(final List<TestIterationDetails> testIterationDetails) {
		Collections.sort(testIterationDetails);
		generateLatestIteraionDetails(testIterationDetails);
		generatePastIteraionSummary(testIterationDetails);
	}

	private static void generateLatestIteraionDetails(final List<TestIterationDetails> testIterationDetails) {
		final TestIterationDetails latIteration = testIterationDetails.get(0);
		final String iterationWorkspace = StringUtils.remove(StringUtils.substringBetween(
				latIteration.getTestIterationDescription(), "TestKonzept_Fehlers_", ".xlsx"), "_Linux");
		System.out.println("\n ***************************************");
		System.out.println("*\tLatest Iteration Details \t*");
		System.out.println(" ***************************************");
		System.out.println("Last iteration number \t\t = " + latIteration.getTestIterationNumber());
		System.out.println("Last iteration date \t\t = "
				+ new SimpleDateFormat(ITERATION_TARGET_DATE_FORMAT).format(latIteration.getTestIterationDate()));
		System.out.println("Last iteration workspace \t = " + iterationWorkspace);
		final StringJoiner environment = new StringJoiner(" and ");
		if (StringUtils.containsIgnoreCase(latIteration.getTestIterationDescription(), "Linux")) {
			environment.add("Linux");
		}
		if (StringUtils.containsIgnoreCase(latIteration.getTestIterationDescription(), "Windows")) {
			environment.add("Windows");
		}
		System.out.println("Last iteration environmet \t = " + environment);
		System.out.println("Next iteration number \t\t = " + latIteration.getNextIterationNumber());
		System.out.println("Next iteration date \t\t = "
				+ new SimpleDateFormat(ITERATION_TARGET_DATE_FORMAT).format(latIteration.getNextIterationDate()));
		System.out.println(" ***************************************");
	}

	private static void generatePastIteraionSummary(final List<TestIterationDetails> testIterationDetails) {
		System.out.println(" *******************************");
		System.out.println("*\tPast Iteration Summary \t*");
		System.out.println(" *******************************");
		final Map<String, MutableInt> summaryMap = new LinkedHashMap<>();
		for (final TestIterationDetails iterationDetails : testIterationDetails) {
			final Date testIterationDate = iterationDetails.getTestIterationDate();
			final String mapKey = new SimpleDateFormat("MMMM").format(testIterationDate) + "_"
					+ new SimpleDateFormat("yyyy").format(testIterationDate);
			if (summaryMap.containsKey(mapKey)) {
				summaryMap.get(mapKey).increment();
			} else {
				summaryMap.put(mapKey, new MutableInt(1));
			}
		}
		for (final Entry<String, MutableInt> summaryMapEntry : summaryMap.entrySet()) {
			final String[] monthAndYear = summaryMapEntry.getKey().split("_");
			System.out.println("Number of Test iterations in " + monthAndYear[0] + " " + monthAndYear[1] + "\t= \t"
					+ summaryMapEntry.getValue());
		}
		System.out.println(" ***************************************");
	}

	public static String getWikiAuthor(final String testIterationDateText) {
		return StringUtils.normalizeSpace(StringUtils.removeIgnoreCase(testIterationDateText, "Von"));
	}

}
