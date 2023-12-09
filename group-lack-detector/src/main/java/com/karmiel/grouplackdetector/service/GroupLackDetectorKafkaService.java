package com.karmiel.grouplackdetector.service;

import com.karmiel.grouplackdetector.dto.ContainerData;
import com.karmiel.grouplackdetector.dto.FullData;
import com.karmiel.grouplackdetector.dto.OrderData;
import com.karmiel.grouplackdetector.entity.Container;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
public class GroupLackDetectorKafkaService {
    @Autowired
    StreamBridge bridge;
    @Autowired
    GroupLackDetectorService service;
    @Value("${threshold:0.5}")
    double threshold;
    @Value("${spring.cloud.stream.bindings.order-out-0.destination:new-required-order}")
    String orderTopic;
    @Value("${spring.cloud.stream.bindings.full-out-0.destination:check-open-order}")
    String fullTopic;

    @Bean
    Consumer<ContainerData> recieveContainerData() {

        return data -> {
            Container container = service.getContainer(data);
            double capacity = container.getProduct().getCapacity();
            double fullness = 0;
            try {
                fullness = data.quantity() / capacity;
            } catch (Exception e) {
                throw new ArithmeticException("Division by zero");
            }
            log.trace("Volume: {} Fullness: {}", capacity, fullness);
            if (fullness < threshold) {
                bridge.send(orderTopic, getOrderData(data, container));
                log.trace("Fullness < 50% send message to topic {} with container ID: {}", orderTopic, data.spotCoordinates());
            } else if (fullness >= threshold) {
                bridge.send(fullTopic, new FullData(data.spotCoordinates()));
                log.trace("Fullness > 50%, send message to topic {} with container ID: {}", fullTopic, data.spotCoordinates());
            }
        };
    }

    private OrderData getOrderData(ContainerData data, Container container) {
        double requiredQuantity = container.getProduct().getCapacity() - data.quantity();
        return new OrderData(data.spotCoordinates(), container.getProduct().getProductName(), requiredQuantity);
    }
}
