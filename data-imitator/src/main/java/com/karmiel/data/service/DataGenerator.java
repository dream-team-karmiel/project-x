package com.karmiel.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@RequiredArgsConstructor
@Configuration
public class DataGenerator {
    public static final int N_MESSAGES = 10;
    public static final int N_CONTAINERS = 10;

    //initial container quantites
    public static final int MIN_QTY = 75;
    public static final int MAX_QTY = 100;

    // decrease amount
    public static final int MIN_DECREASE = 1;
    public static final int MAX_DECREASE = 5;

    //SpotCoordinates
    public static final char MIN_LETTER = 'a';
    public static final char MAX_LETTER = 'e';
    public static final int MIN_NUM = 1;
    public static final int MAX_NUM = 3;

    private static StreamBridge streamBridge;

    static Map<String, Double> containersMap = new HashMap<>();

    public static void main(String[] args) {

        dataDecreaseImitator();

    }

    @Bean
    public static void populateContainerMap() {
        for (int i = 1; i <= N_CONTAINERS; i++) {
            String key = randomKeyGen(MIN_LETTER, MAX_LETTER, MIN_NUM, MAX_NUM);
            containersMap.putIfAbsent(key, (double) (randomInt(MIN_QTY, MAX_QTY)));
        }
    }

    @Bean
    public static void dataDecreaseImitator() {

        populateContainerMap();

        for (int i = 0; i < N_MESSAGES; i++) {
            List<String> keys = containersMap.keySet().stream().toList();
            String randomKey = keys.get(randomInt(0, keys.size()));
            Double value = containersMap.get(randomKey);
                value = value - randomInt(MIN_DECREASE, MAX_DECREASE);
            if (value <= 0) {
                break;
            } else {
                containersMap.put(randomKey, value);
                sendMessage(randomKey, value);
                System.out.println(containersMap);
            }
        }
    }

    public static void sendMessage(String randomKey, Double value) {
//            streamBridge.send("topic-0", new ContainerData(randomKey, value));
    }

    private static int randomInt(int min, int max) {
        return new Random().nextInt(min, max);
    }

    private static String randomKeyGen(char minLetter, char maxLetter, int minNum, int maxNum) {
        char keyLetter = (char) (randomInt(minLetter, maxLetter+1));
        int keyNum = randomInt(minNum, maxNum+1);
        return new String("" + keyLetter + keyNum);
    }
}
