package com.karmiel.backoffice.order.dto;

import com.karmiel.backoffice.order.common.OrderStatus;

import java.time.LocalDateTime;

public record OrderDto(
        String id,
        String spotCoordinates,
        LocalDateTime creationDate,
        LocalDateTime closeDate,
        String productName,
        Double requiredQuantity,
        OrderStatus orderStatus) {
}
