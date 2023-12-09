package com.karmiel.grouplackdetector.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="quantities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
