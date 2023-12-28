package com.karmiel.savedata.controller;

import com.karmiel.savedata.dto.QuantityDto;
import com.karmiel.savedata.service.SavaDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Slf4j
@Controller
public class SaveDataController {
    SavaDataService service;

    @Bean
    Consumer<QuantityDto> receiveNewSensor() {
        return data ->  {
            log.info("Data spotCoordinates {}, quantity {} received", data.spotCoordinates(), data.quantity());
            service.saveData(data);
        };
    }
}
