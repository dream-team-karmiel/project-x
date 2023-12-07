package com.karmiel.grouplackdetector.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="quantities")
@Data
public class Quantity {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "container_id", referencedColumnName = "spot_coordinates")
    private Container container;
    @Column(name = "sensor_date")
    private LocalDateTime sensorDate;
    private Double quantity;
}
