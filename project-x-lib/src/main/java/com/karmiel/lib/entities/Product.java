package com.karmiel.lib.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private int id;
    @Column(name = "product_name")
    private String productName;
    @ManyToOne
    @JoinColumn(name = "measure_id", referencedColumnName = "id")
    private Package packageId;
    private double capacity;





}
