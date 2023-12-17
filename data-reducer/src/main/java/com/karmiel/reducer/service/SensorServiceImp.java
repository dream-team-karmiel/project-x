package com.karmiel.reducer.service;

import com.karmiel.reducer.Sensor;
import com.karmiel.reducer.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorServiceImp implements SensorService {

    private static final Logger logger = LoggerFactory.getLogger(SensorServiceImp.class);


    private final StreamBridge streamBridge;
    private final SensorRepository repository;

    @Value("${spring.cloud.stream.bindings.topic-1.destination:topic-1}")
    private String newTopic;

    @Override
    public void saveSensor(Sensor sensor) {
        Optional<Sensor> optionalSensor = repository.findById(sensor.spotCoordinates());
        if (optionalSensor.isEmpty() || !isEqualQuantity(optionalSensor.get(), sensor)) {
            logger.info("Saving the new sensor state with coordinates: {}", sensor.spotCoordinates());
            repository.save(sensor);
            logger.info("Sending a message about the sensor to the topic '{}'", newTopic);
            streamBridge.send(newTopic, sensor);
        } else {
            logger.info("The sensor status has not changed, saving and sending a message is not required.");
        }

    }

    private boolean isEqualQuantity(Sensor existingSensor, Sensor newSensor) {
        return Objects.equals(newSensor.quantity(), existingSensor.quantity());
    }
}
