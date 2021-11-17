package io.github.bodzisz.service;

import java.util.Random;

public class verificationCodeFactory {

    public static String generateCode() {
        Random random = new Random();
        return Integer.toString((random.nextInt(899_999) + 100_000));
    }
}
