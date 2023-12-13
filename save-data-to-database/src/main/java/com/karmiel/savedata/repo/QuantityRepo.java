package com.karmiel.savedata.repo;

import com.karmiel.savedata.entities.Quantity;
import org.springframework.data.repository.CrudRepository;

public interface QuantityRepo extends CrudRepository<Quantity, Long>
{
}
