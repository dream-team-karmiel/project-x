package com.karmiel.close.record.service.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;
import com.karmiel.close.record.service.enums.OrderStatus;

@AllArgsConstructor
@Setter
@Getter
@Document(collection = "orders")
@Data
public class Order {


    @Id
    String id;
    String spotCoordinates;
    LocalDateTime creationDate;
    LocalDateTime closeDate;
    String productName;
    Double requiredQuantity;
    OrderStatus orderStatus;
}
