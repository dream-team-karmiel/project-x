package com.karmiel.data.container.service;

import com.karmiel.data.container.dto.ContainerData;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
//@EnableAutoConfiguration
//@Configuration
@Service
public class DataGenerator {


    private final StreamBridge streamBridge;



    @Value("${n_messages}")
    private final int N_MESSAGES = 10;
    @Value("${n_containers}")
    private final int N_CONTAINERS = 10;

    @Value("${container_threshold}")
    private final double CONTAINER_THRESHOLD = 0.5;

    //initial container quantites
    @Value("${min_qty}") private final int MIN_QTY = 75;
    @Value("${max_qty}") private final int MAX_QTY = 100;

    // decrease amount
    @Value("${min_decrease}") private final int MIN_DECREASE = 1;
    @Value("${max_decrease}") private final int MAX_DECREASE = 5;

    // increase amount
    @Value("${max_decrease}") private final int MIN_INCREASE = 70;
    @Value("${max_increase}") private final int MAX_INCREASE = 100;

    //SpotCoordinates
    @Value("${spot_letter_from}") private final char MIN_LETTER = 'a';
    @Value("${spot_letter_to}") private final char MAX_LETTER = 'a';
    @Value("${spot_number_from}") private final int MIN_NUM = 1;
    @Value("${spot_number_to}") private final int MAX_NUM = 100;

    private final Map<String, Double> containersMap = new HashMap<>();
    public List<String> keysList;
    public String randomKey;
    public Double value;

    @PostConstruct
    public void populateContainerMap() {
        for (int i = 1; i <= N_CONTAINERS; i++) {
            String key = randomKeyGen(MIN_LETTER, MAX_LETTER, MIN_NUM, MAX_NUM);
            containersMap.putIfAbsent(key, (double) (randomInt(MIN_QTY, MAX_QTY)));
        }
    }

    @Bean
    public void dataImitator() {
        keysList = containersMap.keySet().stream().toList();
        randomKey = keysList.get(randomInt(0, keysList.size()));
        value = containersMap.get(randomKey);

        if (value < 10){
            increaseQty(MIN_INCREASE, MAX_INCREASE);
        }
        else if (value < (CONTAINER_THRESHOLD * 100) ) {
            if (randomInt(0,1) > 0.5) {
                increaseQty(MIN_INCREASE, MAX_INCREASE);
            } else {
                decreaseQty(MIN_DECREASE, MAX_DECREASE);
            }
        }
        else if(value >= CONTAINER_THRESHOLD*100){
            decreaseQty(MIN_DECREASE, MAX_DECREASE);
        }


    }


    public void decreaseQty(int min, int max) {
        for (int i = 1; i <= N_MESSAGES; i++) {
//        while(true){
            value = value - randomInt(min, max);
            if (value <= 0) {
                break;
            } else {
                containersMap.put(randomKey, value);
                sendMessage(randomKey, value);
                System.out.println(containersMap);
            }
        }

    }

    public void increaseQty(int min, int max) {
//        for (int i = 1; i <= N_MESSAGES; i++) {
        while(true){
            List<String> keys = containersMap.keySet().stream().toList();
            String randomKey = keys.get(randomInt(0, keys.size()));
            Double value = containersMap.get(randomKey);
            value = value + randomInt(min, max);
            if (value >= 100) {
                break;
            } else {
                containersMap.put(randomKey, value);
                sendMessage(randomKey, value);
                System.out.println(containersMap);
            }
        }

    }


    public void sendMessage(String randomKey, Double value) {
        streamBridge.send("topic-0", new ContainerData(randomKey, value));
    }

    private int randomInt(int min, int max) {
        return new Random().nextInt(min, max);
    }

    private String randomKeyGen(char minLetter, char maxLetter, int minNum, int maxNum) {
        char keyLetter = (char) (randomInt(minLetter, maxLetter+1));
        int keyNum = randomInt(minNum, maxNum + 1);
        return new String("" + keyLetter + keyNum);
    }
}
