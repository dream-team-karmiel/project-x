package com.karmiel.create.order.record.service.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Document(collection = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash
public class Order {
    @Id
    private String id;
    private String spotCoordinates;
    private LocalDateTime creationDate;
    private LocalDateTime closeDate;
    private String productName;
    private Double requiredQuantity;
    private OrderStatus orderStatus;

    public Order(String id, String spotCoordinates, LocalDateTime creationDate, LocalDateTime closeDate, String productName, Double requiredQuantity, OrderStatus orderStatus) {
        this.id = id;
        this.spotCoordinates = spotCoordinates;
        this.creationDate = creationDate;
        this.closeDate = closeDate;
        this.productName = productName;
        this.requiredQuantity = requiredQuantity;
        this.orderStatus = orderStatus;
    }
}
