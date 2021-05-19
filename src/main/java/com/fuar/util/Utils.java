package com.fuar.util;

import java.security.SecureRandom;
import java.util.Locale;

public class Utils {

    private static final SecureRandom random = new SecureRandom();

    public static String generateKey() {
        return Long.toHexString(random.nextLong()).toUpperCase(Locale.ENGLISH);
    }
}
