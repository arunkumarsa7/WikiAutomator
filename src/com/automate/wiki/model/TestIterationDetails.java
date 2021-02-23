package com.automate.wiki.model;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.automate.wiki.helper.ConfigReader;

public class TestIterationDetails implements Comparable<TestIterationDetails> {

	Integer testIterationNumber;

	Integer testIterationSubNumber;

	Date testIterationDate;

	String wikiAuthor;

	String testIterationDescription;

	Integer nextIterationNumber;

	Date nextIterationDate;

	public TestIterationDetails(final Integer testIterationNumber, final Integer testIterationSubNumber,
			final Date testIterationDate, final String testIterationDescription, final String wikiAuthor) {
		this.testIterationNumber = testIterationNumber;
		this.testIterationSubNumber = testIterationSubNumber;
		this.testIterationDate = testIterationDate;
		this.testIterationDescription = testIterationDescription;
		this.wikiAuthor = wikiAuthor;
		this.nextIterationNumber = testIterationNumber + 1;
		this.nextIterationDate = getNextIterationDate(testIterationDate);
	}

	public Integer getTestIterationNumber() {
		return testIterationNumber;
	}

	public void setTestIterationNumber(final Integer testIterationNumber) {
		this.testIterationNumber = testIterationNumber;
	}

	public Date getTestIterationDate() {
		return testIterationDate;
	}

	public void setTestIterationDate(final Date testIterationDate) {
		this.testIterationDate = testIterationDate;
	}

	public String getTestIterationDescription() {
		return testIterationDescription;
	}

	public void setTestIterationDescription(final String testIterationDescription) {
		this.testIterationDescription = testIterationDescription;
	}

	public Integer getNextIterationNumber() {
		return nextIterationNumber;
	}

	public void setNextIterationNumber(final Integer nextIterationNumber) {
		this.nextIterationNumber = nextIterationNumber;
	}

	public Date getNextIterationDate() {
		return nextIterationDate;
	}

	public void setNextIterationDate(final Date nextIterationDate) {
		this.nextIterationDate = nextIterationDate;
	}

	private Date getNextIterationDate(final Date iterationDate) {
		return DateUtils.addHours(
				Date.from(iterationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
						.with(TemporalAdjusters.next(DayOfWeek.valueOf(ConfigReader.getOutputTargetDay())))
						.atStartOfDay(ZoneId.systemDefault()).toInstant()),
				ConfigReader.getNextIterationDefaultCompletionTime());
	}

	public String getWikiAuthor() {
		return wikiAuthor;
	}

	public void setWikiAuthor(final String wikiAuthor) {
		this.wikiAuthor = wikiAuthor;
	}

	public Integer getTestIterationSubNumber() {
		return testIterationSubNumber;
	}

	public void setTestIterationSubNumber(final Integer testIterationSubNumber) {
		this.testIterationSubNumber = testIterationSubNumber;
	}

	@Override
	public String toString() {
		return "TestIterationDetails [testIterationNumber=" + testIterationNumber + ", testIterationSubNumber="
				+ testIterationSubNumber + ", testIterationDate="
				+ new SimpleDateFormat(ConfigReader.getIterationTargetDateFormat()).format(testIterationDate)
				+ ", wikiAuthor=" + wikiAuthor + ", testIterationDescription=" + testIterationDescription
				+ ", nextIterationNumber=" + nextIterationNumber + ", nextIterationDate="
				+ new SimpleDateFormat(ConfigReader.getIterationTargetDateFormat()).format(nextIterationDate) + "]";
	}

	@Override
	public int compareTo(final TestIterationDetails testIterationDetails) {
		return testIterationDetails.getTestIterationDate().compareTo(this.testIterationDate);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((testIterationDate == null) ? 0 : testIterationDate.hashCode());
		result = prime * result + ((testIterationNumber == null) ? 0 : testIterationNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TestIterationDetails other = (TestIterationDetails) obj;
		if (testIterationDate == null) {
			if (other.testIterationDate != null) {
				return false;
			}
		} else if (!testIterationDate.equals(other.testIterationDate)) {
			return false;
		}
		if (testIterationNumber == null) {
			if (other.testIterationNumber != null) {
				return false;
			}
		} else if (!testIterationNumber.equals(other.testIterationNumber)) {
			return false;
		}
		return true;
	}

}
