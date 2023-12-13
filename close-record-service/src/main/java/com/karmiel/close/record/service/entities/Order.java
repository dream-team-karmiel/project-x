package com.karmiel.close.record.service.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class Order {

    String id;
    String spotCoordinates;
    LocalDateTime creationDate;
    LocalDateTime closeDate;
    String productName;
    Double requiredQuantity;
    String orderStatus;

}
