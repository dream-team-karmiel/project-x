package com.karmiel.backoffice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition
@SpringBootApplication
public class BackOfficeApp {
    public static void main(String[] args) {
        SpringApplication.run(BackOfficeApp.class);
    }
}
