package com.karmiel.lib.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "measures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Measures {
    @Id
    private int id;
    @Column(name = "measure_name")
    private String measureName;

}
