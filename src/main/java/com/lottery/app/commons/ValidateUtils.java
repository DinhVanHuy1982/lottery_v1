package com.lottery.app.commons;

public class ValidateUtils {

    public static boolean stringEmpty(String value) {
        if (value == null || value.isEmpty() || value.trim().isBlank()) {
            return true;
        }
        return false;
    }
}
