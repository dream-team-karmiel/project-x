package com.karmiel.groupfulldetector.service;

import com.karmiel.groupfulldetector.dto.FullData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class FullDetectorKafka {
    private final FullDetectorImpl service;

    @Bean
    public Consumer<FullData> receiveFullData() {
        return data -> {
            log.trace("Get message {}", data);
            service.orderStatusProcessing(data);
        };
    }
}