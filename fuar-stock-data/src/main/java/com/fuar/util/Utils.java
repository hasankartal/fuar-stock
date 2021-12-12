package com.fuar.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Locale;

public class Utils {

    private static final SecureRandom random = new SecureRandom();

    public static String generateKey() {
        return Long.toHexString(random.nextLong()).toUpperCase(Locale.ENGLISH);
    }

    public static String hashCode(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(hash);

        return encoded;
    }
}
