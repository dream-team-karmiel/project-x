package com.karmiel.grouplackdetector;

import com.karmiel.grouplackdetector.dto.ContainerData;
import com.karmiel.grouplackdetector.dto.FullData;
import com.karmiel.grouplackdetector.dto.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class GroupLackDetectorApp {
    @Autowired
    StreamBridge bridge;
    double threshold = 50;
    double volume = 100;

    public static void main(String[] args) {
        SpringApplication.run(GroupLackDetectorApp.class);
    }

    @Bean
    Consumer<ContainerData> recieveContainerData() {

        return data -> {

            double fullness = volume / 100 * data.quantity();
            if (fullness < threshold) {
                System.out.println("Volume < 50%");
                bridge.send("order-out-0",
                        new OrderData(data.spotCoordinates(), "TestProductName", volume - data.quantity()));
            } else if (fullness >= threshold) {
                System.out.println("Volume > 50%");
                bridge.send("full-out-0", new FullData(data.spotCoordinates()));
            }
        };
    }
}
