package com.karmiel.grouplackdetector.entity;

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
    @ManyToOne
    @JoinColumn(name = "container_id", referencedColumnName = "spot_coordinates")
    private Container container;
    private LocalDateTime sensorDate;
    private double quantity;


}
