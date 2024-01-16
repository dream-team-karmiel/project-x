package com.karmiel.backoffice.container.repository;

import com.karmiel.backoffice.container.model.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityRepository extends JpaRepository<Quantity, Integer> {
}
