package com.karmiel.savedata;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.savedata.dto.Sensor;
import com.karmiel.savedata.repo.QuantityRepo;
import com.karmiel.savedata.service.SaveDataToDatabase;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.function.Consumer;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;



@SpringBootTest
public class SaveRecordToDatabaseTest
{

    @MockBean
    private QuantityRepo repo;

    @InjectMocks
    private SaveDataToDatabase saveDataToDatabase;

    @Test
    public void positiveTests() throws Exception
    {
        ObjectMapper jsonmapper = new ObjectMapper();
        Sensor s1 = new Sensor("A10", 40.);

        String inputData1 = null;
        inputData1 = jsonmapper.writeValueAsString(s1);

        //Sensor s2 = new Sensor("X250", 0.);
        //Sensor s3 = new Sensor("X250", 5.5);

        // consumer

        Consumer<String> consumer = saveDataToDatabase.receiveSensorData();
        consumer.accept(inputData1);

        // assert
        verify(repo, times(1)).save(any());

    }

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
        Consumer<String> consumer = saveDataToDatabase.receiveSensorData();
        consumer.accept(inputData1);
        consumer.accept(inputData2);
        consumer.accept(inputData3);
        consumer.accept(inputData4);
        consumer.accept(inputData5);

        // assert
        verify(repo, times(1)).save(any());

    }


}
