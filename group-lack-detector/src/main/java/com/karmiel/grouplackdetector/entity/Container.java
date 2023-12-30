package com.karmiel.grouplackdetector.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "containers")
public class Container {
    @Id
    @Column(name = "spot_coordinates")
    private String spotCoordinates;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;





}
