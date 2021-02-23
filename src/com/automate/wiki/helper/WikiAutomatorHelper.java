package com.automate.wiki.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;

import com.automate.wiki.model.LatestTestIterationDetails;
import com.automate.wiki.model.TestIterationDetails;
import com.automate.wiki.util.WikiAutomatorUtils;

public class WikiAutomatorHelper {

	private WikiAutomatorHelper() {

	}

	public static void generateSummaryReport(final List<TestIterationDetails> testIterationDetails,
			final boolean isPrintWikiSummary) {
		generateLatestIteraionDetails(testIterationDetails, isPrintWikiSummary);
		if (isPrintWikiSummary) {
			generatePastIteraionSummary(testIterationDetails);
		}
	}

	private static void generateLatestIteraionDetails(final List<TestIterationDetails> testIterationDetails,
			final boolean isPrintWikiSummary) {
		final TestIterationDetails latIteration = testIterationDetails.get(0);
		LatestTestIterationDetails.testIterationDetails = latIteration;
		final String iterationWorkspace = StringUtils.remove(StringUtils.substringBetween(
				latIteration.getTestIterationDescription(), "TestKonzept_Fehlers_", ".xlsx"), "_Linux");
		if (isPrintWikiSummary) {
			System.out.println("\n ***************************************");
			System.out.println("*\tLatest Iteration Details \t*");
			System.out.println(" ***************************************");
			System.out.println("Last iteration number \t\t = " + latIteration.getTestIterationNumber());
			System.out.println("Last iteration date \t\t = " + new SimpleDateFormat(ConfigReader.getSummaryDateFormat())
					.format(latIteration.getTestIterationDate()));
			System.out.println("Last iteration workspace \t = " + iterationWorkspace);
		}
		final StringJoiner environment = new StringJoiner(" and ");
		if (StringUtils.containsIgnoreCase(latIteration.getTestIterationDescription(), "Linux")) {
			environment.add("Linux");
		}
		if (StringUtils.containsIgnoreCase(latIteration.getTestIterationDescription(), "Windows")) {
			environment.add("Windows");
		}
		if (isPrintWikiSummary) {
			System.out.println("Last iteration environmet \t = " + environment);
			System.out.println("Next iteration number \t\t = " + latIteration.getNextIterationNumber());
			System.out.println("Next iteration date \t\t = " + new SimpleDateFormat(ConfigReader.getSummaryDateFormat())
					.format(latIteration.getNextIterationDate()));
			System.out.println(" ***************************************");
		}
	}

	private static void generatePastIteraionSummary(final List<TestIterationDetails> testIterationDetails) {
		System.out.println(" ***************************************");
		System.out.println("*\tRecent Iteration Summary \t*");
		System.out.println(" ***************************************");
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

	public static void main(final String[] args) {
		generateLatestWikiEntryForDisplay();
	}

	public static void generateLatestWikiEntryForDisplay() {
		final TestIterationDetails testIterationDetails = LatestTestIterationDetails
				.getLatestTestIterationDetails(false);
		final Date nextIterationDate = WikiAutomatorUtils.getTestIterationDate(
				new SimpleDateFormat(ConfigReader.getConversionDateFormat()).format(Calendar.getInstance().getTime()),
				Calendar.getInstance().getTimeZone(), TimeZone.getTimeZone(ConfigReader.getTargetTimezone()));
		final String iterationWorkspace = ConfigReader.getIterationWorkspace();
		final boolean isIterationForLinux = ConfigReader.isIterationDoneLinux();
		final boolean isIterationForWindows = ConfigReader.isIterationDoneWindows();
		final int iterationNumber = testIterationDetails.getNextIterationNumber();
		final String iterationDate = new SimpleDateFormat(ConfigReader.getIterationWikiDisplayDateFormat())
				.format(nextIterationDate) + " Uhr";

		System.out.println(" *************************************** ");
		System.out.println("*\tLatest Wiki Entry Details \t*");
		System.out.println(" *************************************** ");
		System.out.println("Iteration workspace\t = " + iterationWorkspace);
		System.out.println("Iteration date\t\t = " + iterationDate);
		System.out.println("Iteration number\t = " + iterationNumber);
		System.out.println("Iteration in Linux\t = " + isIterationForLinux);
		System.out.println("Iteration in Windows\t = " + isIterationForWindows);

		final StringBuilder htmlBuilder = new StringBuilder("\n");
		htmlBuilder.append(iterationDate);
		htmlBuilder.append("\n\n");
		htmlBuilder.append("Testfallfixing End-of-Test-Iteration-");
		htmlBuilder.append(iterationNumber);
		htmlBuilder.append("\n\n");
		htmlBuilder.append("Die  Excelliste zum Testfallfixing  befindet sich unter ");
		if (isIterationForLinux) {
			htmlBuilder.append(ConfigReader.getIterationLinuxOutLocationForDisplay());
			htmlBuilder.append(" mit ");
			htmlBuilder.append(new SimpleDateFormat("dd-MM-yyy").format(nextIterationDate));
			htmlBuilder.append(" für ");
			htmlBuilder.append(iterationWorkspace);
			htmlBuilder.append(" Linux ");
		}
		if (isIterationForWindows) {
			htmlBuilder.append("und ");
			htmlBuilder.append(ConfigReader.getIterationWindowsOutLocationForDisplay());
			htmlBuilder.append(" mit ");
			htmlBuilder.append(new SimpleDateFormat("dd-MM-yyy").format(nextIterationDate));
			htmlBuilder.append(" für ");
			htmlBuilder.append(iterationWorkspace);
			htmlBuilder.append(" Host/Windows.");
		}
		htmlBuilder.append("\n\n");
		htmlBuilder.append("von AzTech India");
		System.out.println("Entwicklernews entry = ");
		System.out.println(htmlBuilder.toString());
	}

	public static String generateLatestWikiEntryForEdit() {
		final TestIterationDetails testIterationDetails = LatestTestIterationDetails
				.getLatestTestIterationDetails(false);
		final Date nextIterationDate = WikiAutomatorUtils.getTestIterationDate(
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
		htmlBuilder.append(
				new SimpleDateFormat(ConfigReader.getIterationWikiDisplayDateFormat()).format(nextIterationDate));
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
			htmlBuilder.append(ConfigReader.getIterationLinuxOutLocation());
			htmlBuilder.append(" mit ");
			htmlBuilder.append(new SimpleDateFormat("dd-MM-yyy").format(nextIterationDate));
			htmlBuilder.append(" für ");
			htmlBuilder.append(iterationWorkspace);
			htmlBuilder.append(" Linux ");
		}
		if (isIterationForWindows) {
			htmlBuilder.append("und ");
			htmlBuilder.append(ConfigReader.getIterationWindowsOutLocation());
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

	public static void generateDetailedSummaryReport(final List<TestIterationDetails> childTestIterationDetails) {
		populateIterationNumberIfEmpty(childTestIterationDetails);
		final Map<String, List<TestIterationDetails>> detailedIterationSummaryDataMap = new LinkedHashMap<>();
		System.out.println(" ***********************************************");
		System.out.println("*\tDetailed Iteration Summary Report \t*");
		System.out.println(" ***********************************************");
		for (final TestIterationDetails iterationDetails : childTestIterationDetails) {
			String wikiAuthor = StringUtils.isNotBlank(iterationDetails.getWikiAuthor())
					? iterationDetails.getWikiAuthor()
					: "Guest";
			wikiAuthor = StringUtils.containsIgnoreCase(wikiAuthor, "von")
					? StringUtils.removeIgnoreCase(wikiAuthor, "von")
					: wikiAuthor;
			final String testIterationYear = new SimpleDateFormat("yyy")
					.format(iterationDetails.getTestIterationDate());
			final String detailedIterationSummaryMapKey = testIterationYear + "-" + wikiAuthor.trim();
			if (detailedIterationSummaryDataMap.containsKey(detailedIterationSummaryMapKey)) {
				detailedIterationSummaryDataMap.get(detailedIterationSummaryMapKey).add(iterationDetails);
			} else {
				final List<TestIterationDetails> testIterationDetails = new ArrayList<>();
				testIterationDetails.add(iterationDetails);
				detailedIterationSummaryDataMap.put(detailedIterationSummaryMapKey, testIterationDetails);
			}
		}
		for (final Entry<String, List<TestIterationDetails>> iterationDetails : detailedIterationSummaryDataMap
				.entrySet()) {
			final String[] keys = iterationDetails.getKey().split("-");
			String author = StringUtils.isNotBlank(keys[1]) ? keys[1] : "Guest";
			author = author.substring(0, Math.min(author.length(), 12));
			System.out.println("Total number of iterations  done by '" + author + "' in " + keys[0] + " = "
					+ iterationDetails.getValue().size());
		}
		System.out.println(" ***********************************************");
	}

	private static void populateIterationNumberIfEmpty(final List<TestIterationDetails> childTestIterationDetails) {
		Integer testIterationNumber = 0;
		for (final TestIterationDetails iterationDetails : childTestIterationDetails) {
			testIterationNumber = iterationDetails.getTestIterationNumber() != 0
					? iterationDetails.getTestIterationNumber()
					: testIterationNumber;
			if (iterationDetails.getTestIterationNumber() == 0) {
				iterationDetails.setTestIterationNumber(testIterationNumber + 1);
			}
		}
	}

}
