package com.govt_exam_preparation.others;

import java.util.regex.Pattern;

public class Emailvalidation {

	private final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(

			"^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
					+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
					+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
					+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
					+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
					+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$");

	private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z ]*$");

	private static final Pattern PASSWORD_PATTERN = Pattern
			.compile("^[a-zA-Z0-9+_.]*${4,16}");

	private static final Pattern MobilePattern = Pattern
			.compile("^[0-9+]{7,13}$");

	private static final Pattern NumberPattern = Pattern
			.compile("^[0-9]*$");

	// add this method:

	public boolean checkEmail(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}

	public boolean checkName(String name) {
		return NAME_PATTERN.matcher(name).matches();
	}

	public boolean checkPassword(String password) {
		return PASSWORD_PATTERN.matcher(password).matches();
	}

	public boolean checkMobile(String mobile) {
		return MobilePattern.matcher(mobile).matches();
	}

	public boolean checkNumber(String number) {
		return NumberPattern.matcher(number).matches();
	}

	/*public boolean checkEmail(String email) {
		return NAME_PATTERN.matcher(email).matches();
	}*/
}
