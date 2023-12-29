package com.karmiel.data;

import com.karmiel.data.container.service.DataGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DataImitatorApp implements CommandLineRunner {
    @Autowired
    private DataGenerator generator;
    public static void main(String[] args) {
        SpringApplication.run(DataImitatorApp.class);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Running the imitator");
        generator.start();
    }
}
