package com.karmiel.grouplackdetector.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "orders")
@Data
public class Orders {
    @Id
    String id;
    String spotCoordinates;
    LocalDateTime creationDate;
    LocalDateTime closeDate;
    String productName;
    Double requiredQuantity;
    OrderStatus orderStatus;
}
