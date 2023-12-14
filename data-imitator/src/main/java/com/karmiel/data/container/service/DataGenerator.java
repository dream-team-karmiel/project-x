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
@Service
public class DataGenerator {

    private final StreamBridge streamBridge;

    @Value("${n_messages:100}")
    private final int N_MESSAGES;
    @Value("${n_containers:10}")
    private final int N_CONTAINERS;

    @Value("${container_threshold:0.5}")
    private final double CONTAINER_THRESHOLD;
    @Value("${container_lowest:0.1}")
    private final double CONTAINER_LOWEST;

    //initial container quantites
    @Value("${min_qty:75}")
    private final int MIN_QTY;
    @Value("${max_qty:100}")
    private final int MAX_QTY;

    // decrease amount
    @Value("${min_decrease:5}")
    private final int MIN_DECREASE;
    @Value("${max_decrease:10}")
    private final int MAX_DECREASE;

    // increase amount
    @Value("${min_increase:70}")
    private final int MIN_INCREASE;
    @Value("${max_increase:100}")
    private final int MAX_INCREASE;

    //SpotCoordinates
    @Value("${spot_letter_from:'A'}")
    private final char MIN_LETTER;
    @Value("${spot_letter_to:'B'}")
    private final char MAX_LETTER;
    @Value("${spot_number_from:1}")
    private final int MIN_NUM;
    @Value("${spot_number_to:10}")
    private final int MAX_NUM;

    public final String bindingName = "sendMessage-out-0";
    public final Map<String, Double> containersMap = new HashMap<>();
    public List<String> keysList;
    public String selectKey;
    public Double value;

    @PostConstruct
    public void populateContainerMap() {
        for (int i = 1; i <= N_CONTAINERS; i++) {
            String randomKey = "" + MIN_LETTER + i;
            Double randomValue = (double) randomInt(MIN_QTY, MAX_QTY);
            containersMap.put(randomKey, randomValue);
        }
    }

    @Bean
    public void dataImitator() {
        keysList = containersMap.keySet().stream().toList();

        while (true) {
            selectKey = keysList.get(randomInt(0, N_CONTAINERS));
            value = containersMap.get(selectKey);
            containersMap.put(selectKey, changeValue(value));
            sendMessage(selectKey, value);
        }
    }

    public double changeValue(double value) {
        int chngAmnt;
        int chngProb;
        double res = value;

        if (value <= CONTAINER_LOWEST * 100) {
            chngAmnt = randomInt(MIN_INCREASE, MAX_INCREASE);
            chngProb = new Random().nextInt((0), 2); // 0 to 1
            double temp = value + chngAmnt * chngProb;
            res = temp < 0 ? 0 : temp;

        } else if (value > 10 && value <= CONTAINER_THRESHOLD * 100) {
            chngProb = new Random().nextInt((-1), 2); // -1 to 1
            chngAmnt = chngProb < 0 ? randomInt(MIN_DECREASE, MAX_DECREASE) : randomInt(MIN_INCREASE, MAX_INCREASE);
            double temp = value + chngAmnt * chngProb;
            if (temp > 100)
                res = 100;
            else if (temp < 0)
                res = 0;
            else res = temp;

        } else if (value > CONTAINER_THRESHOLD * 100) {
            chngAmnt = randomInt(MIN_DECREASE, MAX_DECREASE);
            chngProb = new Random().nextInt((-1), 1); // -1 to 0
            double temp = value + chngAmnt * chngProb;
            res = temp > 100 ? 100 : temp;
        }
        return res;
    }

    public void sendMessage(String selectKey, Double value) {
        streamBridge.send(bindingName, new ContainerData(selectKey, value));
    }

    private int randomInt(int min, int max) {
        return new Random().nextInt(min, max);
    }

    private String randomKeyGen(char minLetter, char maxLetter, int minNum, int maxNum) {
        char keyLetter = (char) (randomInt(minLetter, maxLetter));
        int keyNum = randomInt(minNum, maxNum + 1);
        return "" + keyLetter + keyNum;
    }

    private String randomKeyGen(char minLetter, int MAX_NUM) {
        int keyNum = randomInt(1, MAX_NUM + 1);
        return "" + minLetter + keyNum;
    }
}