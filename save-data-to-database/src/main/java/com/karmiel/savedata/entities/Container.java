package com.karmiel.savedata.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "containers")
@Data
public class Container {
    @Id
    @Column(name = "spot_coordinates")
    private String spotCoordinates;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}