package com.karmiel.grouplackdetector.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quantities")
public class Quantity {
    @Id
    public int id;
    @ManyToOne
    public Container container;


}
