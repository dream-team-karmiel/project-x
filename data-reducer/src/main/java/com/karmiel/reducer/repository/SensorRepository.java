package com.karmiel.reducer.repository;

import com.karmiel.reducer.Sensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends CrudRepository<Sensor, String> {
}
