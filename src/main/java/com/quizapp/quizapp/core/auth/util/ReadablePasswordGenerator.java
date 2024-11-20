package com.quizapp.quizapp.core.auth.util;

import net.datafaker.Faker;
import java.security.SecureRandom;

public class ReadablePasswordGenerator {
    private static final Faker faker = new Faker();
    private static final SecureRandom random = new SecureRandom();
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_-+=<>?";

    public static String generateStrongPassword() {
        String adjective = capitalizeFirstLetter(faker.color().name());
        String name = capitalizeFirstLetter(faker.name().lastName());

        int number = random.nextInt(100);

        char specialChar = SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length()));

        return adjective + name + number + specialChar;
    }

    private static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
