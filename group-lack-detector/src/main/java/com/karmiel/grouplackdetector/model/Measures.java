package com.karmiel.grouplackdetector.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="measures")
@Data
public class Measures {
    @Id
    private Long id;
    @Column(name = "measure_name")
    private String measureName;
}
