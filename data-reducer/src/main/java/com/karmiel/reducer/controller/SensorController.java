package com.karmiel.reducer.controller;

import com.karmiel.reducer.Sensor;
import com.karmiel.reducer.service.SensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Controller
public class SensorController {
    private static final Logger logger = LoggerFactory.getLogger(SensorController.class);
    private final SensorService service;


    public SensorController(SensorService service) {
        this.service = service;
    }

    @Bean
    public Consumer<Sensor> sensorConsumer() {
        return sensor -> {
            logger.trace("Sensor processing with coordinates: {}, and value: {}", sensor.spotCoordinates(), sensor.quantity());
            service.saveSensor(sensor);
        };
    }
}