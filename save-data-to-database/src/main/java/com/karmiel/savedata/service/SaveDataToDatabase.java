package com.karmiel.savedata.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.savedata.dto.Sensor;
import com.karmiel.savedata.entities.Quantity;
import com.karmiel.savedata.repo.QuantityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@Service
public class SaveDataToDatabase
{
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    QuantityRepo repo;

    public Consumer<String> receiveSensorData()
    {
        return data->{
            Sensor s = null;

            try
            {
                s = mapper.readValue(data, Sensor.class);
            } catch (JsonProcessingException e)
            {
                throw new RuntimeException(e);
            }

            var time = LocalDateTime.now();

            Quantity q = new Quantity();
            q.setQuantity(s.quantity());
            q.setContainerId(s.spotCoordinates());
            q.setSensorDate(time);

            repo.save(q);
        };
    }
}
