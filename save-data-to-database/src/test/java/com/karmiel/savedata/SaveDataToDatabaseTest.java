package com.karmiel.savedata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.savedata.dto.Sensor;
import com.karmiel.savedata.entities.Quantity;
import com.karmiel.savedata.repo.QuantityRepo;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
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
        String inputData1;


        ObjectMapper jsonmapper = new ObjectMapper();
        Sensor s1 = new Sensor("A10", 40.);
        inputData1 = jsonmapper.writeValueAsString(s1);
        Quantity q = new Quantity(100L, "A3", LocalDateTime.now(), 100.0);
        when(repo.save(any(Quantity.class))).thenReturn(q);
        producer.send(new GenericMessage<>(inputData1), bindingName);
        verify(repo, times(1)).save(any(Quantity.class));


        // System.out.println(inputData1);


    }


}
