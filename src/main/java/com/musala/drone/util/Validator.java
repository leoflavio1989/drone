package com.musala.drone.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Validator {

	private final static Pattern NUMBER_REGEX_P = Pattern.compile("^\\d+$");
	private final static Pattern NAME_REGEX_P = Pattern.compile("^[A-Za-z0-9_-]*$");
	private final static Pattern CODE_REGEX_P = Pattern.compile("^[A-Z0-9_]*$");
	
	public static boolean isValidString(String value) {
		return StringUtils.isNotBlank(value);
	}
	
	public static boolean isValidSerialNumber(String value) {
		if(isValidString(value)) {
			if(value.length() <= 100) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isValidMedicationName(String value) {
		if(isValidString(value)) {
			return NAME_REGEX_P.matcher(value).matches();
		}
		return false;
	}
	
	public static boolean isValidMedicationCode(String value) {
		if(isValidString(value)) {
			return CODE_REGEX_P.matcher(value).matches();
		}
		return false;
	}

	public static boolean isValidInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isValidNumber(String value) {
		if (NUMBER_REGEX_P.matcher(value).matches()) {
			return true;
		}
		return false;
	}

}
