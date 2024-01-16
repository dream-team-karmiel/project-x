package com.karmiel.backoffice.container.dto;

import com.karmiel.backoffice.container.model.Container;

import java.time.LocalDateTime;

public record QuantityDto(
        int id,
        Container container,
        LocalDateTime sensorDate,
        double quantity) {}
