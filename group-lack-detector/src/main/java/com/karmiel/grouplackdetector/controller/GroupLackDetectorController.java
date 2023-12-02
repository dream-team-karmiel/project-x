package com.karmiel.grouplackdetector.controller;

import com.karmiel.grouplackdetector.dto.Sensor;
import com.karmiel.grouplackdetector.service.GroupLackDetectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GroupLackDetectorController {

    private final GroupLackDetectorService service;

    @Bean
    Consumer<Sensor> receiveSensorData() {
        return sensor -> {
            log.trace("Received sensor: {}", sensor);
            service.detect(sensor);
        };
    }

}
