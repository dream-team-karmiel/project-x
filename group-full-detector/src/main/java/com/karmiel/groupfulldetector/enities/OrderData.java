package com.karmiel.groupfulldetector.enities;

import com.karmiel.groupfulldetector.utils.OrderStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;
import java.time.LocalDateTime;

@RedisHash
@Document(collection = "orders")
@Data
public class OrderData {
    @Id
    String id;
    String spotCoordinates;
    LocalDateTime creationDate;
    LocalDateTime closeDate;
    String productName;
    Double requiredQuantity;
    OrderStatus orderStatus;
}