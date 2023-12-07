package com.karmiel.savedata.repository;

import com.karmiel.savedata.dto.Sensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends CrudRepository<Sensor, String> {
}
