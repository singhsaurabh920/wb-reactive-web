package org.wb.reactive.web.utils;

import java.util.Random;

public interface RandomNumberUtils {

    public static int generateRandom(int upperRange){
        Random random = new Random();
        return random.nextInt(upperRange);
    }

    public static int generateRandomBetween(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
