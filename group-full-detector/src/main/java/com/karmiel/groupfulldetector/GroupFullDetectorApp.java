package com.karmiel.groupfulldetector;

import com.karmiel.groupfulldetector.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class GroupFullDetectorApp {

    @Autowired
    ItemRepository ordersRepo;
    public static void main(String[] args) {
        SpringApplication.run(GroupFullDetectorApp.class, args);
    }
}
