package com.karmiel.backoffice.order.dto;

import java.time.LocalDateTime;

public record OrderCreateRequestDto(
        String spotCoordinates,
        LocalDateTime creationDate,
        Double requiredQuantity) {
}
