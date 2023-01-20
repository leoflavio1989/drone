package com.musala.drone.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {

	@Test
	public void isValidStringShouldReturnTrueWhenParameterIsValidString() {
		assertTrue(Validator.isValidString("jasdjn"));
		assertTrue(Validator.isValidString("515165"));
		assertTrue(Validator.isValidString("   asd   "));
		assertTrue(Validator.isValidString("adswe34234 "));
		assertTrue(Validator.isValidString("acdwe234$#$#"));
		assertTrue(Validator.isValidString("  acdwe2   34$#$#  "));
	}

	@Test
	public void isValidStringShouldReturnFalseWhenParameterIsNotValidString() {
		assertFalse(Validator.isValidString(null));
		assertFalse(Validator.isValidString(""));
		assertFalse(Validator.isValidString("  "));
	}

	@Test
	public void isValidIntegerShouldReturnTrueWhenParameterIsInteger() {
		assertTrue(Validator.isValidInteger("212454784"));
		assertTrue(Validator.isValidInteger("1245"));
		assertTrue(Validator.isValidInteger("1010"));
	}

	@Test
	public void isValidIntegerShouldReturnFalseWhenParameterIsNotInteger() {
		assertFalse(Validator.isValidInteger(null));
		assertFalse(Validator.isValidInteger(""));
		assertFalse(Validator.isValidInteger("  "));
		assertFalse(Validator.isValidInteger("1511515s"));
		assertFalse(Validator.isValidInteger(" e51515122 "));
		assertFalse(Validator.isValidInteger("5454475.5"));
		assertFalse(Validator.isValidInteger(" 8545475465 "));
		assertFalse(Validator.isValidInteger(" 2145 "));
		assertFalse(Validator.isValidInteger("55r5"));
		assertFalse(Validator.isValidInteger(" 4asd "));
	}
}
