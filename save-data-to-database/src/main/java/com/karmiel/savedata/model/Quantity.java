package com.karmiel.savedata.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="quantities")
public class Quantity {
    @Id
    private long id;
    private String containerId;
    private LocalDateTime sensorDate;
    private double quantity;
}

