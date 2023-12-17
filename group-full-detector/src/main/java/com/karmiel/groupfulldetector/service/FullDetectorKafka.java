package com.karmiel.groupfulldetector.service;

import com.karmiel.groupfulldetector.dto.FullData;
import com.karmiel.groupfulldetector.dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import java.util.function.Consumer;

@Slf4j
@Service
public class FullDetectorKafka {
    @Autowired
    StreamBridge bridge;
    @Autowired
    FullDetectorImpl impl;
    @Value("${spring.cloud.stream.bindings.full-out-0.destination:close-order}")
    String closeOrderTopic;

    @Bean
    public Consumer<FullData> receiveFullData() {
        return data -> {
            String orderId = impl.checkOrder(data);
            if (orderId != null) {
                bridge.send(closeOrderTopic, new Order(orderId));
                log.trace("Order ID:{}", orderId);
            }
        };
    }
}
