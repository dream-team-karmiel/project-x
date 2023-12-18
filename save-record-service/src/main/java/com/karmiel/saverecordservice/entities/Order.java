package com.karmiel.saverecordservice.entities;

import com.karmiel.saverecordservice.dto.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash
@Document(collection = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private String id;
    private String spotCoordinates;
    private LocalDateTime creationDate;
    private LocalDateTime closeDate;
    private String productName;
    private Double requiredQuantity;
    private OrderStatus orderStatus;
}
