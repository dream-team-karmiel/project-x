package com.karmiel.lib.entities;

import com.karmiel.lib.dto.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
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