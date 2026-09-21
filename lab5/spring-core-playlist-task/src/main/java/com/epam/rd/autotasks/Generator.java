package com.epam.rd.autotasks;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class Generator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int MIN_LENGTH = 5, MAX_LENGTH = 15;

    public String generateString(){
        int n = RANDOM.nextInt(MIN_LENGTH, MAX_LENGTH + 1);
        return generateRandomString(n);
    }

    public String generateRandomString(int length) {
        if (length < 1)
            throw new IllegalArgumentException("Длина строки должна быть больше 0");
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
}