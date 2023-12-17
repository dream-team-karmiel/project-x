package com.karmiel.reducer.service;

import com.karmiel.reducer.Sensor;
import com.karmiel.reducer.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorServiceImp implements SensorService {

    private final StreamBridge streamBridge;
    private final SensorRepository repository;

    @Value("${spring.cloud.stream.bindings.topic-1.destination:topic-1}")
    private String newTopic;

    @Override
    public void saveSensor(Sensor sensor) {
        Optional<Sensor> optionalSensor = repository.findById(sensor.spotCoordinates());
        if (optionalSensor.isEmpty() || notEqualQuantity(optionalSensor.get(), sensor)) {
            repository.save(sensor);
            streamBridge.send(newTopic, sensor);
        }
    }

    private boolean notEqualQuantity(Sensor existingSensor, Sensor newSensor) {
        return !newSensor.quantity().equals(existingSensor.quantity());
    }
}
