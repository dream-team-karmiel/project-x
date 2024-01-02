package com.karmiel.backoffice.order.model;

import com.karmiel.backoffice.order.common.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
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
