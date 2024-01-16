package com.karmiel.backoffice.quantity.repository;

import com.karmiel.backoffice.quantity.entity.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuantityRepository extends JpaRepository<Quantity, Integer> {
    List<Quantity> findByContainerId(String containerId);
}
