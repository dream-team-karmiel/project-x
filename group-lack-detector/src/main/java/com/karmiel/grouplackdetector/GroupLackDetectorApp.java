package com.karmiel.grouplackdetector;

import com.karmiel.grouplackdetector.dto.ContainerData;
import com.karmiel.grouplackdetector.dto.FullData;
import com.karmiel.grouplackdetector.dto.OrderData;
import com.karmiel.grouplackdetector.entity.Container;
import com.karmiel.grouplackdetector.service.GroupLackDetectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class GroupLackDetectorApp {
    @Autowired
    StreamBridge bridge;
    @Autowired
    GroupLackDetectorService service;
    @Value("${THRESHOLD:0.5}")
    double threshold;
    @Value("${spring.cloud.stream.bindings.order-out-0.destination}")
    String orderTopic;
    @Value("${spring.cloud.stream.bindings.full-out-0.destination}")
    String fullTopic;

    public static void main(String[] args) {
        SpringApplication.run(GroupLackDetectorApp.class);
    }

    @Bean
    Consumer<ContainerData> recieveContainerData() {

        return data -> {
            Container container = service.getContainer(data);
            service.saveContainer(container);
            double volume = container.quantity;
            double fullness = data.quantity() / volume;
            System.out.println("Volume: "+volume+" Fullness: "+fullness);
            if (fullness < threshold) {
                System.out.println("Fullness < 50%");
                bridge.send(orderTopic,
                        new OrderData(data.spotCoordinates(), container.product.productName, volume - data.quantity()));
            } else if (fullness >= threshold) {
                System.out.println("Fullness > 50%");
                bridge.send(fullTopic, new FullData(data.spotCoordinates()));
            }
        };
    }
}
