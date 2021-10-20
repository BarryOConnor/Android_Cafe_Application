package com.barry.ntufood;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidationHelperTest {

    // Testing null or empty validator
    @Test
    public void isNullOrEmpty_empty() {
        assertTrue(ValidationHelper.isNullOrEmpty(""));
    }

    @Test
    public void isNullOrEmpty_null() {
        assertTrue(ValidationHelper.isNullOrEmpty(null));
    }

    @Test
    public void isNullOrEmpty_notEmpty() {
        assertFalse(ValidationHelper.isNullOrEmpty("something"));
    }

    // Testing password strength validation function

    @Test
    public void isValidPassword_empty() {
        assertFalse(ValidationHelper.isValidPassword(""));
    }

    @Test
    public void isValidPassword_null() {
        assertFalse(ValidationHelper.isValidPassword(null));
    }

    @Test
    public void isValidPassword_invalid_noNumber() {
        assertFalse(ValidationHelper.isValidPassword("Someth!ng"));
    }

    @Test
    public void isValidPassword_invalid_noPunct() {
        assertFalse(ValidationHelper.isValidPassword("Someth1ng"));
    }

    @Test
    public void isValidPassword_invalid_tooShort() {
        assertFalse(ValidationHelper.isValidPassword("S0m!"));
    }

    @Test
    public void isValidPassword_valid() {
        assertTrue(ValidationHelper.isValidPassword("S0m3th!ng"));
    }

    // Testing email validation function

    @Test
    public void isValidEmail_CorrectEmail() {
        assertTrue(ValidationHelper.isValidEmail("name@email.com"));
    }
    @Test
    public void isValidEmail_CorrectCOUKSubDomain() {
        assertTrue(ValidationHelper.isValidEmail("name@email.co.uk"));
    }
    @Test
    public void isValidEmail_invalidEmailNoTld() {
        assertFalse(ValidationHelper.isValidEmail("name@email"));
    }
    @Test
    public void isValidEmail_invalidEmailTooManyDots() {
        assertFalse(ValidationHelper.isValidEmail("name@email..com"));
    }
    @Test
    public void isValidEmail_invalidEmailNoUsername() {
        assertFalse(ValidationHelper.isValidEmail("@email.com"));
    }
    @Test
    public void isValidEmail_invalidEmptyString() {
        assertFalse(ValidationHelper.isValidEmail(""));
    }
    @Test
    public void isValidEmail_invalidNull() {
        assertFalse(ValidationHelper.isValidEmail(null));
    }
}