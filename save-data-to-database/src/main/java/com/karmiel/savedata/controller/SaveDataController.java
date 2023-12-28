package com.karmiel.savedata.controller;

import com.karmiel.savedata.dto.QuantityDto;
import com.karmiel.savedata.service.SavaDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SaveDataController {
    private final SavaDataService service;

    @Bean
    Consumer<QuantityDto> receiveNewData() {
        return data ->  {
            log.info("Data spotCoordinates {}, quantity {} received", data.spotCoordinates(), data.quantity());
            service.saveData(data);
        };
    }
}
