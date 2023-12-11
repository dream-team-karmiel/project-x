package com.karmiel.savedata.repo;

import com.karmiel.savedata.entities.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityRepo extends JpaRepository<Quantity, Long>
{
}
