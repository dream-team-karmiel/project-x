package com.karmiel.savedata.repository;

import com.karmiel.savedata.model.Quantity;
import org.springframework.data.repository.CrudRepository;

public interface QuantityRepository extends CrudRepository<Quantity, Long> {
}
