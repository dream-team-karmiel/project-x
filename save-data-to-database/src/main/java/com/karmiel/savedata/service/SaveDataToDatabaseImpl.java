package com.karmiel.savedata.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.savedata.dto.Sensor;
import com.karmiel.savedata.entities.Quantity;
import com.karmiel.savedata.repo.QuantityRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaveDataToDatabaseImpl implements SaveDataToDatabase
{
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    final QuantityRepo repo;

    @Bean
    public Consumer<String> receiveSensorData()
    {
        return data ->
        {
            if (data != null)
            {
                Sensor s = null;
                try
                {
                    s = mapper.readValue(data, Sensor.class);
                    var time = LocalDateTime.now();

                    Quantity q = new Quantity();
                    q.setQuantity(s.quantity());
                    q.setContainerId(s.spotCoordinates());
                    q.setSensorDate(time);

                    var saved = repo.save(q);

                    log.trace("Quantity {} saved to db", saved);

                } catch (JsonProcessingException e)
                {
                    log.trace(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
