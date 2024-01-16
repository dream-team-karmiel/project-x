package com.karmiel.backoffice.container.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "containers")
public class Container {
    @Id
    @Column(name = "spot_coordinates")
    private String spotCoordinates;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Measure measure;
    @OneToMany
    private List<Quantity> quantities;

}
