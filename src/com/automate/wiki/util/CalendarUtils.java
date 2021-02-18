package com.automate.wiki.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

import com.automate.wiki.helper.ConfigReader;

public class CalendarUtils {

	private CalendarUtils() {

	}

	public static Date getTestIterationDate(final String testIterationDateText, final TimeZone fromTimeZone,
			final TimeZone toTimeZone) {
		final Calendar calendar = Calendar.getInstance();
		try {
			final Date iterationDate = new SimpleDateFormat(ConfigReader.getConversionDateFormat())
					.parse(StringUtils.normalizeSpace(
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

}
