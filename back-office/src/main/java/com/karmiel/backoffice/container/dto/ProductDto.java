package com.karmiel.backoffice.container.dto;

import com.karmiel.backoffice.container.model.Measure;

public record ProductDto(
        int id,
        String productName,
        Measure measure,
        double capacity) {}
