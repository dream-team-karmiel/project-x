package com.karmiel.backoffice.container.repository;

import com.karmiel.backoffice.container.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
