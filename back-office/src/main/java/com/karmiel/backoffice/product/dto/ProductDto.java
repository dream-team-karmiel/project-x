package com.karmiel.backoffice.product.dto;

import com.karmiel.backoffice.container.entity.Measure;

public record ProductDto(
        int id,
        String productName,
        Measure measure,
        double capacity) {}
