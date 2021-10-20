package com.barry.ntufood;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {
    //class to hold all the various validation routines needed by the app

    public final static boolean isValidPassword(final String input) {
        /*
        function to validate a password against the password policy, which requires one upper, one lower, one numeric and one of "@#$%^&+=!".
        This is done by using a regular expression and matching against this pattern.
        - input : input takes the password in string form
        - returns: whether the string matches the pattern
        */
        if (isNullOrEmpty(input)){
            return false;
        }

        Pattern pwPattern;
        Matcher patternMatcher;
        final String password_regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        pwPattern = Pattern.compile(password_regexp);
        patternMatcher = pwPattern.matcher(input);
        return patternMatcher.matches();
    }

    public final static boolean isValidEmail(final String input) {
        /*
        function to validate an email using the built in email checking functionality
        - input : input takes the email in string form
        - returns: whether the string matches the pattern for a valid email
        */
        if (isNullOrEmpty(input)){
            return false;
        }

        Pattern emailPattern;
        Matcher patternMatcher;
        final String email_regexp = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+";
        emailPattern = Pattern.compile(email_regexp);
        patternMatcher = emailPattern.matcher(input);
        return patternMatcher.matches();
    }

    public final static boolean isNullOrEmpty(final String input) {
        /*
        function to check whether a string is null or empty
        - input : input takes the input in string form
        - returns: whether the string is null/empty or has content
        */
        return input == null || input.length() == 0;
    }
}
