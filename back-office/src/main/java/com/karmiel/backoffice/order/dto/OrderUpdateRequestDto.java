package com.karmiel.backoffice.order.dto;

import com.karmiel.backoffice.order.common.OrderStatus;

public record OrderUpdateRequestDto(
        String id,
        Double requiredQuantity,
        OrderStatus orderStatus) {
}
