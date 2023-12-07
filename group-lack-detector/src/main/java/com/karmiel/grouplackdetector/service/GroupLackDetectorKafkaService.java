package com.karmiel.grouplackdetector.service;

import com.karmiel.grouplackdetector.dto.ContainerData;
import com.karmiel.grouplackdetector.dto.FullData;
import com.karmiel.grouplackdetector.dto.OrderData;
import com.karmiel.grouplackdetector.entity.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class GroupLackDetectorKafkaService {
    @Autowired
    StreamBridge bridge;
    @Autowired
    GroupLackDetectorService service;
    @Value("${threshold:0.5}")
    double threshold;
    @Value("${spring.cloud.stream.bindings.order-out-0.destination}")
    String orderTopic;
    @Value("${spring.cloud.stream.bindings.full-out-0.destination}")
    String fullTopic;

    @Bean
    Consumer<ContainerData> recieveContainerData() {

        return data -> {
            Container container = service.getContainer(data);
            double capacity = container.product.capacity;
            double fullness = data.quantity() / capacity;
            System.out.println("Volume: "+capacity+" Fullness: "+fullness);
            if (fullness < threshold) {
                System.out.println("Fullness < 50%");
                bridge.send(orderTopic,
                        new OrderData(data.spotCoordinates(), container.product.productName, capacity - data.quantity()));
            } else if (fullness >= threshold) {
                System.out.println("Fullness > 50%");
                bridge.send(fullTopic, new FullData(data.spotCoordinates()));
            }
        };
    }

}
