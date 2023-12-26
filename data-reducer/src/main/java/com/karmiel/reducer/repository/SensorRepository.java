package com.karmiel.reducer.repository;

import com.karmiel.reducer.dto.Sensor;
import org.springframework.data.repository.CrudRepository;

public interface SensorRepository extends CrudRepository<Sensor, String> {
}
