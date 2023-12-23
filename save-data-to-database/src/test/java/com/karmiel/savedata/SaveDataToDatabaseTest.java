package com.karmiel.savedata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.savedata.dto.Sensor;
import com.karmiel.savedata.entities.Quantity;
import com.karmiel.savedata.repo.QuantityRepo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@Slf4j
@Import(TestChannelBinderConfiguration.class)
public class SaveDataToDatabaseTest
{
    @Resource
    InputDestination producer;

    @MockBean
    QuantityRepo repo;

    String bindingName = "receiveSensorData-in-0";

    @Test
    public void positiveTests() throws Exception
    {
        Sensor sensor1 = new Sensor("A10", 40.05);
        Quantity quantityEntry = new Quantity(1L, sensor1.spotCoordinates(), LocalDateTime.now(), sensor1.quantity());
        when(repo.save(any(Quantity.class))).thenReturn(quantityEntry);
        when(repo.save(any(Quantity.class))).thenAnswer(ans ->
        {
            Quantity qe = ans.getArgument(0);
            assertEquals(qe.getQuantity(), quantityEntry.getQuantity());
            assertEquals(qe.getContainerId(), quantityEntry.getContainerId());
            log.info(qe.toString());
            return qe;
        });
        producer.send(new GenericMessage<>(sensor1), bindingName);
        verify(repo, times(1)).save(any(Quantity.class));

    }

}
