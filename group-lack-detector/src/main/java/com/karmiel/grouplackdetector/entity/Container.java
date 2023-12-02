package com.karmiel.grouplackdetector.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Container {
    @Id
    public int spotCoordinatesId;
    public double quantity;
    @ManyToOne
    public Product product;


    public int spotCoordinatesId() {
        return spotCoordinatesId;
    }

    public double quantity() {
        return quantity;
    }

    public Product product() {
        return product;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Container) obj;
        return this.spotCoordinatesId == that.spotCoordinatesId &&
                Double.doubleToLongBits(this.quantity) == Double.doubleToLongBits(that.quantity) &&
                Objects.equals(this.product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spotCoordinatesId, quantity, product);
    }

    @Override
    public String toString() {
        return "Container[" +
                "spotCoordinatesId=" + spotCoordinatesId + ", " +
                "quantity=" + quantity + ", " +
                "product=" + product + ']';
    }

}
