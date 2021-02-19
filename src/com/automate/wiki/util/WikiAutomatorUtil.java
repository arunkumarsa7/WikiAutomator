package com.automate.wiki.util;

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

import com.automate.wiki.helper.ConfigReader;
import com.automate.wiki.model.LatestTestIterationDetails;
import com.automate.wiki.model.TestIterationDetails;

public class WikiAutomatorUtil {

	private WikiAutomatorUtil() {

	}

	public static void generateSummaryReport(final List<TestIterationDetails> testIterationDetails) {
		Collections.sort(testIterationDetails);
		generateLatestIteraionDetails(testIterationDetails);
		generatePastIteraionSummary(testIterationDetails);
	}

	private static void generateLatestIteraionDetails(final List<TestIterationDetails> testIterationDetails) {
		final TestIterationDetails latIteration = testIterationDetails.get(0);
		LatestTestIterationDetails.testIterationDetails = latIteration;
		final String iterationWorkspace = StringUtils.remove(StringUtils.substringBetween(
				latIteration.getTestIterationDescription(), "TestKonzept_Fehlers_", ".xlsx"), "_Linux");
		System.out.println("\n ***************************************");
		System.out.println("*\tLatest Iteration Details \t*");
		System.out.println(" ***************************************");
		System.out.println("Last iteration number \t\t = " + latIteration.getTestIterationNumber());
		System.out.println("Last iteration date \t\t = " + new SimpleDateFormat(ConfigReader.getSummaryDateFormat())
				.format(latIteration.getTestIterationDate()));
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
		System.out.println("Next iteration date \t\t = " + new SimpleDateFormat(ConfigReader.getSummaryDateFormat())
				.format(latIteration.getNextIterationDate()));
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
			System.out.println("Number of Test iterations in " + monthAndYear[0] + "\t" + monthAndYear[1] + " = "
					+ summaryMapEntry.getValue());
		}
		System.out.println(" ***************************************");
	}

	public static String getWikiAuthor(final String testIterationDateText) {
		return StringUtils.normalizeSpace(StringUtils.removeIgnoreCase(testIterationDateText, "Von"));
	}

	public static String generateLatestWikiEntry() {
		final TestIterationDetails testIterationDetails = LatestTestIterationDetails.getLatestTestIterationDetails();
		final Date nextIterationDate = CalendarUtils.getTestIterationDate(
				new SimpleDateFormat(ConfigReader.getConversionDateFormat()).format(Calendar.getInstance().getTime()),
				Calendar.getInstance().getTimeZone(), TimeZone.getTimeZone(ConfigReader.getTargetTimezone()));
		final String iterationWorkspace = ConfigReader.getIterationWorkspace();
		final boolean isIterationForLinux = ConfigReader.isIterationDoneLinux();
		final boolean isIterationForWindows = ConfigReader.isIterationDoneWindows();

		final String HTML_ELEMENT_EM_START = "<em><em>";

		final String HTML_ELEMENT_EM_END = "</em></em>";

		final StringBuilder htmlBuilder = new StringBuilder("arguments[0]");
		htmlBuilder.append(".insertAdjacentHTML('afterend', '");
		htmlBuilder.append("<h3 id=\\\"");
		htmlBuilder.append("Entwicklernews-");
		htmlBuilder.append(new SimpleDateFormat("dd.MM.yyy-HH:mm").format(nextIterationDate));
		htmlBuilder.append("Uhr");
		htmlBuilder.append("\\\">");
		htmlBuilder.append(HTML_ELEMENT_EM_START);
		htmlBuilder.append(new SimpleDateFormat("dd.MM.yyy - HH:mm").format(nextIterationDate));
		htmlBuilder.append("&nbsp; Uhr&nbsp;");
		htmlBuilder.append(HTML_ELEMENT_EM_END);
		htmlBuilder.append("</h3>");
		htmlBuilder.append("<h4 id=\\\"");
		htmlBuilder.append("Entwicklernews-TestfallfixingEnd-of-Test-Iteration-");
		htmlBuilder.append(testIterationDetails.getNextIterationNumber());
		htmlBuilder.append("\\\">");
		htmlBuilder.append(HTML_ELEMENT_EM_START);
		htmlBuilder.append("Testfallfixing End-of-Test-Iteration-");
		htmlBuilder.append(testIterationDetails.getNextIterationNumber());
		htmlBuilder.append(HTML_ELEMENT_EM_END);
		htmlBuilder.append("</h4>");
		htmlBuilder.append("<p class=\\\"diff-block-target\\\">");
		htmlBuilder.append(HTML_ELEMENT_EM_START);
		htmlBuilder.append("Die Excelliste zum Testfallfixing befindet sich unter ");
		if (isIterationForLinux) {
			htmlBuilder.append("\\\"P:\\\\IT-KRAFT\\\\unsere Dokumente\\\\ABS\\\\Einarbeiter\\\\TestKonzept_Fehlers_");
			htmlBuilder.append(iterationWorkspace);
			htmlBuilder.append("_Linux");
			htmlBuilder.append(".xlsx\\\"");
			htmlBuilder.append(" mit ");
			htmlBuilder.append(new SimpleDateFormat("dd-MM-yyy").format(nextIterationDate));
			htmlBuilder.append(" für ");
			htmlBuilder.append(iterationWorkspace);
			htmlBuilder.append(" Linux ");
		}
		if (isIterationForWindows) {
			htmlBuilder.append("und ");
			htmlBuilder.append("\\\"P:\\\\IT-KRAFT\\\\unsere Dokumente\\\\ABS\\\\Einarbeiter\\\\TestKonzept_Fehlers_");
			htmlBuilder.append(iterationWorkspace);
			htmlBuilder.append(".xlsx\\\"");
			htmlBuilder.append(" mit ");
			htmlBuilder.append(new SimpleDateFormat("dd-MM-yyy").format(nextIterationDate));
			htmlBuilder.append("&nbsp;");
			htmlBuilder.append("für ");
			htmlBuilder.append(iterationWorkspace);
			htmlBuilder.append(" Host/Windows.");
		}
		htmlBuilder.append("&nbsp;");
		htmlBuilder.append(HTML_ELEMENT_EM_END);
		htmlBuilder.append("</p>");
		htmlBuilder.append("<p class=\\\"diff-block-target\\\">");
		htmlBuilder.append(HTML_ELEMENT_EM_START);
		htmlBuilder.append("von AzTech India");
		htmlBuilder.append(HTML_ELEMENT_EM_END);
		htmlBuilder.append("</p>");
		htmlBuilder.append("<hr>');");
		return htmlBuilder.toString();
	}

}
