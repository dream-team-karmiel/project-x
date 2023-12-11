package com.karmiel.savedata.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    private Long id;
    @Column(name = "product_name")
    private String productName;
    @ManyToOne
    @JoinColumn(name = "measure_id", referencedColumnName = "id")
    private Measures measure;
    private Double capacity;
}