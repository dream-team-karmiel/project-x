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
    private String spotCoordinatesId;
    @ManyToOne
    private Product product;





}
