package com.karmiel.grouplackdetector.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    private int id;
    private String productName;
    @ManyToOne
    @JoinColumn(name = "measure_id", referencedColumnName = "id")
    private Package packageId;
    private double capacity;





}
