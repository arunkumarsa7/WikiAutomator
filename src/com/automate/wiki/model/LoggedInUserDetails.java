package com.automate.wiki.model;

import org.apache.commons.lang3.StringUtils;

import com.automate.wiki.service.WikiLoginHelper;

public class LoggedInUserDetails {

	public static String loggedInUsername;

	private LoggedInUserDetails() {

	}

	public static String getLoggedInUsername() {
		if (StringUtils.isEmpty(loggedInUsername)) {
			performLoginAndPopulateUserDetails();
		}
		return loggedInUsername;
	}

	private static void performLoginAndPopulateUserDetails() {
		final WikiLoginHelper loginHelper = new WikiLoginHelper();
		loginHelper.loginToWiki();
	}

}
