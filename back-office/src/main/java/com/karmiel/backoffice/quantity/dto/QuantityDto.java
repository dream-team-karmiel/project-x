package com.karmiel.backoffice.quantity.dto;

import com.karmiel.backoffice.container.entity.Container;

import java.time.LocalDateTime;

public record QuantityDto(
        int id,
        Container container,
        LocalDateTime sensorDate,
        double quantity) {}
