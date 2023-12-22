package com.karmiel.lib.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "containers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Container {
    @Id
    @Column(name = "spot_coordinates")
    private String spotCoordinatesId;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}