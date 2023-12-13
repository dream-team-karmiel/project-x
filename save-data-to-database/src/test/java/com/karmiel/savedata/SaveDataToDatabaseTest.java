package com.karmiel.savedata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.savedata.dto.Sensor;
import com.karmiel.savedata.entities.Quantity;
import com.karmiel.savedata.repo.QuantityRepo;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.messaging.support.GenericMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
public class SaveDataToDatabaseTest
{

    //@Resource
    //InputDestination producer;

    @MockBean
    QuantityRepo repo;

    String bindingName = "receiveSensorData-in-0";

    @Test
    public void positiveTests() throws Exception
    {

        ObjectMapper jsonmapper = new ObjectMapper();
        Sensor s1 = new Sensor("A10", 40.);

        String inputData1;
        inputData1 = jsonmapper.writeValueAsString(s1);

        System.out.println(inputData1);

        // assert

        when(repo.save(any(Quantity.class))).thenReturn(new Quantity());

        //producer.send(new GenericMessage<>(s1), bindingName);

        verify(repo, times(1)).save(any(Quantity.class));

    }

    /*
    @Test
    public void negativeTests() throws Exception
    {
        ObjectMapper jsonmapper = new ObjectMapper();
        Sensor s1 = new Sensor("", 40.);
        Sensor s2 = new Sensor("asdf", -5.);
        Sensor s3 = new Sensor("", null);
        Sensor s4 = new Sensor(null, 0.);

        String inputData1 = null;
        inputData1 = jsonmapper.writeValueAsString(s1);
        String inputData2 = null;
        inputData2 = jsonmapper.writeValueAsString(s2);
        String inputData3 = null;
        inputData3 = jsonmapper.writeValueAsString(s3);
        String inputData4 = null;
        inputData4 = jsonmapper.writeValueAsString(s4);

        String inputData5 = "wrong data }";

        // consumer
        Consumer<String> consumer = saveDataToDatabaseImpl.receiveSensorData();
        consumer.accept(inputData1);
        consumer.accept(inputData2);
        consumer.accept(inputData3);
        consumer.accept(inputData4);
        consumer.accept(inputData5);

        // assert
        verify(repo, times(1)).save(any());

    }*/


}
