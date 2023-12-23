package com.karmiel.lib.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quantities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quantity {
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "container_id", referencedColumnName = "spot_coordinates")
    private Container container;
    @Column(name = "sensor_date")
    private LocalDateTime sensorDate;
    private double quantity;
}