package com.karmiel.savedata.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="quantities")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Quantity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Column(name = "container_id")
    public String containerId;
    @Column(name = "sensor_date")
    public LocalDateTime sensorDate;
    public Double quantity;

}