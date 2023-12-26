package com.karmiel.reducer.controller;

import com.karmiel.reducer.dto.SensorDto;
import com.karmiel.reducer.service.SensorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;


@Configuration
@Slf4j
public class SensorController {
    private final SensorService service;

    public SensorController(SensorService service) {
        this.service = service;
    }

    @Bean
    public Consumer<SensorDto> sensorConsumer() {
        return sensorDto -> {
            log.info("Sensor processing with coordinates: {}, and value: {}", sensorDto.spotCoordinates(), sensorDto.quantity());
            service.saveSensor(sensorDto);
        };
    }
}