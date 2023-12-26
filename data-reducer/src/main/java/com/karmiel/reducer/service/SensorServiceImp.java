package com.karmiel.reducer.service;

import com.karmiel.reducer.dto.Sensor;
import com.karmiel.reducer.dto.SensorDto;
import com.karmiel.reducer.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorServiceImp implements SensorService {

    private static final Logger logger = LoggerFactory.getLogger(SensorServiceImp.class);


    private final StreamBridge streamBridge;
    private final SensorRepository repository;

    @Value("${spring.cloud.stream.bindings.sendMessage-out-0.destination:topic-1}")
    private String newTopic;

    @Override
    public void saveSensor(SensorDto sensorDto) {
        log.info("Before repository");
        Optional<Sensor> optionalSensor = repository.findById(sensorDto.spotCoordinates());
        log.info("After repository");
        if (optionalSensor.isEmpty() || !isEqualQuantity(optionalSensor.get(), sensorDto)) {
            logger.info("Saving the new sensorDto state with coordinates: {}", sensorDto.spotCoordinates());
            repository.save(new Sensor(sensorDto.spotCoordinates(), sensorDto.quantity()));
            logger.info("Sending a message about the sensorDto to the topic '{}'", newTopic);
            streamBridge.send(newTopic, sensorDto);
        } else {
            logger.info("The sensorDto status has not changed, saving and sending a message is not required.");
        }

    }

    private boolean isEqualQuantity(Sensor existingSensor, SensorDto newSensor) {
        return Objects.equals(newSensor.quantity(), existingSensor.getQuantity());
    }
}
