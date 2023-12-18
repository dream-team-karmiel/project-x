package com.karmiel.reducer.controller;

import com.karmiel.reducer.Sensor;
import com.karmiel.reducer.service.SensorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Controller
@Slf4j
public class SensorController {
    private final SensorService service;

    public SensorController(SensorService service) {
        this.service = service;
    }

    @Bean
    public Consumer<Sensor> sensorConsumer() {
        return sensor -> {
            log.trace("Sensor processing with coordinates: {}, and value: {}", sensor.spotCoordinates(), sensor.quantity());
            service.saveSensor(sensor);
        };
    }
}