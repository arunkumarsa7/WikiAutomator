package com.automate.wiki.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

import com.automate.wiki.helper.ConfigReader;

public class WikiAutomatorUtils {

	private WikiAutomatorUtils() {

	}

	public static Date getTestIterationDate(final String testIterationDateText, final TimeZone fromTimeZone,
			final TimeZone toTimeZone) {
		final Calendar calendar = Calendar.getInstance();
		try {
			final Date iterationDate = new SimpleDateFormat(ConfigReader.getConversionDateFormat())
					.parse(StringUtils.deleteWhitespace(
							StringUtils.remove(StringUtils.removeIgnoreCase(testIterationDateText, "Uhr"), "-")));
			calendar.setTime(iterationDate);
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

	public static String replaceLast(final String string, final String substring, final String replacement) {
		final int index = string.lastIndexOf(substring);
		if (index == -1) {
			return string;
		}
		return string.substring(0, index) + replacement + string.substring(index + substring.length());
	}

}
