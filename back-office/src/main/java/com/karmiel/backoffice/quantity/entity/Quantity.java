package com.karmiel.backoffice.quantity.entity;

import com.karmiel.backoffice.container.entity.Container;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quantities")
public class Quantity {
    @Id
    private int id;
    private String containerId;
    private LocalDateTime sensorDate;
    private double quantity;


}
