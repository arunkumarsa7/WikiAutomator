package com.automate.wiki;

public class TestClass {

	public static void main(final String[] args) {
		try {
			System.out.println("try");
			return;
		} catch (final Exception e) {
			System.out.println("catch");
			return;
		} finally {
			System.out.println("finally");
			return;
		}

	}

}
