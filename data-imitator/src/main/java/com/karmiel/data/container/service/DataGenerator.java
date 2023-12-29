package com.karmiel.data.container.service;

import com.karmiel.data.container.dto.ContainerData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataGenerator {

    private final StreamBridge streamBridge;

    private final Random random = new Random();

    @Value("${com.karmiel.project-x.data-imitator.n_containers:10}")
    private int N_CONTAINERS;

    @Value("${com.karmiel.project-x.data-imitator.container_threshold:0.5}")
    private double CONTAINER_THRESHOLD;
    @Value("${com.karmiel.project-x.data-imitator.container_lowest:0.1}")
    private double CONTAINER_LOWEST;

    //SpotCoordinates
    @Value("${com.karmiel.project-x.data-imitator.spot_letter_from:A}")
    private char MIN_LETTER;

    public final String bindingName = "sendMessage-out-0";
    public final Map<String, Double> containersMap = new HashMap<>();

    public void start() throws InterruptedException {
        populateContainerMap();
        dataImitator();
    }

    public void populateContainerMap() {
        for (int i = 1; i <= N_CONTAINERS; i++) {
            String number = (i < 10) ? "0" + i : String.valueOf(i);
            String randomKey = MIN_LETTER + number;
            containersMap.put(randomKey, 100.);
        }
        log.trace("Containers are created");
    }

    public void dataImitator() throws InterruptedException {
        log.info("Start sending messages");
        while (true) {
            for (Map.Entry<String, Double> entry : containersMap.entrySet()) {
                entry.setValue(changeValue(entry.getValue()));
                sendMessage(entry.getKey(), entry.getValue());
            }
            Thread.sleep(5000);
        }
    }

    public double changeValue(double value) {
        double res = value;

        if (value <= CONTAINER_LOWEST * 100) {
            res = 100.;
        } else if (value > CONTAINER_LOWEST * 100 && value <= CONTAINER_THRESHOLD * 100) {
            boolean isIncrease = new Random().nextInt(5) == 0;
            if (isIncrease) {
                res = 100;
            } else {
                if (random.nextBoolean()) {
                    res = decrease(value);
                }
            }
        } else if (value > CONTAINER_THRESHOLD * 100) {
            if (random.nextBoolean()) {
                res = decrease(value);
            }
        }

        res = Math.round(res * 1000.0) / 1000.0;
        return res;
    }

    private double decrease(double value) {
        return value - random.nextDouble() * 5;
    }

    public void sendMessage(String selectKey, Double value) {
        streamBridge.send(bindingName, new ContainerData(selectKey, value));
        log.info("Message container {} value {} sent", selectKey, value);
    }
}