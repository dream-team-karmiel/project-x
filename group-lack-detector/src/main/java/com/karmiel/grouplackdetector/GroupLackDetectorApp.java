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
       public static void main(String[] args) {
        SpringApplication.run(GroupLackDetectorApp.class);
    }


}
