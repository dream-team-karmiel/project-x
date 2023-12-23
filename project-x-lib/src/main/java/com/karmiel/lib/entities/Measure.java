package com.karmiel.lib.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "measures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Measure {
    @Id
    private int id;
    @Column(name = "measure_name")
    private String measureName;
}