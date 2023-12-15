package com.karmiel.groupfulldetector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GroupFullDetectorApp {

    public static void main(String[] args) {
        SpringApplication.run(GroupFullDetectorApp.class, args);
    }
}
