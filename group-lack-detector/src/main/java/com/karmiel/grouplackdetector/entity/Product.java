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
public class Product {
    @Id
    public int id;
    public String productName;
    @ManyToOne
    public Package packageId;
    public int supplierId;
    public int capacityToContainer;



    public int id() {
        return id;
    }

    public String productName() {
        return productName;
    }

    public Package packageId() {
        return packageId;
    }

    public int supplierId() {
        return supplierId;
    }

    public int capacityToContainer() {
        return capacityToContainer;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Product) obj;
        return this.id == that.id &&
                Objects.equals(this.productName, that.productName) &&
                Objects.equals(this.packageId, that.packageId) &&
                this.supplierId == that.supplierId &&
                this.capacityToContainer == that.capacityToContainer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, packageId, supplierId, capacityToContainer);
    }

    @Override
    public String toString() {
        return "Product[" +
                "id=" + id + ", " +
                "productName=" + productName + ", " +
                "packageId=" + packageId + ", " +
                "supplierId=" + supplierId + ", " +
                "capacityToContainer=" + capacityToContainer + ']';
    }

}
