package com.karmiel.backoffice.product.dto;

import com.karmiel.backoffice.container.dto.MeasureDto;
import com.karmiel.backoffice.container.entity.Measure;

public record ProductDto(
        int id,
        String productName,
        MeasureDto measure,
        double capacity) {}
