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

    @Value("${n_messages}")
    private final int N_MESSAGES = 300;
    @Value("${n_containers}")
    private final int N_CONTAINERS = 5;

    @Value("${container_threshold}")
    private final double CONTAINER_THRESHOLD = 0.5;
    @Value("${container_lowest}")
    private final double CONTAINER_LOWEST = 0.1;

    //initial container quantites
    @Value("${min_qty}")
    private final int MIN_QTY = 75;
    @Value("${max_qty}")
    private final int MAX_QTY = 100;

    // decrease amount
    @Value("${min_decrease}")
    private final int MIN_DECREASE = 5;
    @Value("${max_decrease}")
    private final int MAX_DECREASE = 10;

    // increase amount
    @Value("${max_decrease}")
    private final int MIN_INCREASE = 70;
    @Value("${max_increase}")
    private final int MAX_INCREASE = 100;

    //SpotCoordinates
    @Value("${spot_letter_from}")
    private final char MIN_LETTER = 'A';
    @Value("${spot_letter_to}")
    private final char MAX_LETTER = 'B';
    @Value("${spot_number_from}")
    private final int MIN_NUM = 1;
    @Value("${spot_number_to}")
    private final int MAX_NUM = N_CONTAINERS;

    public final Map<String, Double> containersMap = new HashMap<>();
    public List<String> keysList;
    public String selectKey;
    public Double value;

    @PostConstruct
    public void populateContainerMap() {
        while (containersMap.size() < N_CONTAINERS) {
            String randomKey = randomKeyGen(MIN_LETTER, MAX_NUM);
            while (!containersMap.containsKey(randomKey)) {
                value = (double) randomInt(MIN_QTY, MAX_QTY);
                containersMap.put(randomKey, value);
            }
        }
    }

    @Bean
    public void dataImitator(){
        keysList = containersMap.keySet().stream().toList();
        int count=0;
        while(count < N_MESSAGES){
            selectKey = keysList.get(randomInt(0, N_CONTAINERS));
            Double value = containersMap.get(selectKey);
            containersMap.put(selectKey, changeValue(value));
            sendMessage(selectKey, value);
            count++;
        }

    }


    public double changeValue(double value) {
        int chngAmnt;   //3
        int chngProb;
        double res = value;

        if (value <= CONTAINER_LOWEST * 100) {
            chngAmnt = randomInt(MIN_INCREASE, MAX_INCREASE); // 3
            chngProb = new Random().nextInt((0), 2);
            double temp = value + chngAmnt * chngProb;
            res = temp < 0 ? 0 : temp;

        } else if (value > 10 && value <= CONTAINER_THRESHOLD * 100) {
            chngProb = new Random().nextInt((-1), 2);
            chngAmnt = chngProb < 0 ? randomInt(MIN_DECREASE, MAX_DECREASE) : randomInt(MIN_INCREASE, MAX_INCREASE);
            double temp = value + chngAmnt * chngProb;
            if (temp > 100)
                res = 100;
            else if (temp < 0)
                res = 0;
            else res = temp;

        } else if (value > CONTAINER_THRESHOLD * 100) {
            chngAmnt = randomInt(MIN_DECREASE, MAX_DECREASE);
            chngProb = new Random().nextInt((-1), 1);
            double temp = value + chngAmnt * chngProb;
            res = temp > 100 ? 100 : temp;
        }
        return res;
    }

    public void sendMessage(String randomKey, Double value) {
        streamBridge.send("topic-0", new ContainerData(randomKey, value));
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
