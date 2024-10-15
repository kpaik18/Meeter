package com.example.meeter.core.link.service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomLinkGenerator {
    private static final SecureRandom random = new SecureRandom();

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String ALL = UPPERCASE + LOWERCASE + NUMBERS;

    public static String generateRandomString() {
        return generate(16, 16, 16, 48);
    }

    private static String generate(int nUppercase, int nLowercase, int nNumbers, int maxlength) {
        int fill = maxlength - (nUppercase + nLowercase + nNumbers);
        if (fill < 0) {
            throw new IllegalArgumentException();
        }
        return shuffle(
                selectRandomTokens(nUppercase, UPPERCASE)
                        + selectRandomTokens(nLowercase, LOWERCASE)
                        + selectRandomTokens(nNumbers, NUMBERS)
                        + selectRandomTokens(fill, ALL)
        );
    }

    private static String shuffle(String s) {
        List<String> tokens = Arrays.asList(s.split(""));
        Collections.shuffle(tokens);
        return String.join("", tokens);
    }

    private static String selectRandomTokens(int n, String tokens) {
        StringBuilder randomTokens = new StringBuilder();

        for (int i = 0; i < n; i++) {
            randomTokens.append(tokens.charAt(random.nextInt(tokens.length())));
        }
        return randomTokens.toString();
    }
}
