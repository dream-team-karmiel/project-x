package com.karmiel.reducer.controller;


import com.karmiel.reducer.Sensor;
import com.karmiel.reducer.service.SensorService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Controller
public class SensorController {
    private final SensorService service;

    public SensorController(SensorService service) {
        this.service = service;
    }

    @Bean
    public Consumer<Sensor> sensorConsumer() {
        return service::saveSensor;
    }
}