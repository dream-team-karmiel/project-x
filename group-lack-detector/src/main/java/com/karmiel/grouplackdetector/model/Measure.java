package com.karmiel.grouplackdetector.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="measures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Measure {
    @Id
    private Long id;
    @Column(name = "measure_name")
    private String measureName;

}
