package com.karmiel.grouplackdetector.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private Long id;
    @Column(name = "product_name")
    private String productName;
    @ManyToOne
    @JoinColumn(name = "measure_id", referencedColumnName = "id")
    private Measure measure;
    private Double capacity;
}
