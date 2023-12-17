package com.karmiel.reducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.reducer.repository.SensorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
public class DataReducerAppTest {
    private static final String SENSOR_LOCATION = "location1";
    private static final String TOPIC_NAME = "topic-1";
    private static final String CONSUMER_BINDING_NAME = "sensorConsumer-in-0";

    @Autowired(required = false)
    InputDestination producer;
    @Autowired(required = false)
    OutputDestination consumer;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SensorRepository repository;

    @Test
    public void shouldReceiveAndSaveNewSensorDataWhenSensorIsNotInRepository() throws IOException {
        Sensor newSensor = new Sensor(SENSOR_LOCATION, 100.0);
        when(repository.findById(SENSOR_LOCATION)).thenReturn(Optional.empty());
        setupAndTestSensor(newSensor, true);
        verify(repository, times(1)).findById(SENSOR_LOCATION);
        verify(repository, times(1)).save(newSensor);
    }

    @Test
    public void shouldIgnoreSensorDataIfSensorAlreadyExistsInRepository() throws IOException {
        Sensor existingSensor = new Sensor(SENSOR_LOCATION, 100.0);
        when(repository.findById(SENSOR_LOCATION)).thenReturn(Optional.of(existingSensor));
        setupAndTestSensor(existingSensor, false);
        verify(repository, times(1)).findById(SENSOR_LOCATION);
        verify(repository, never()).save(any(Sensor.class));
    }

    @Test
    public void shouldReceiveAndSaveSensorDataWithUpdatedQuantityIfExistsInRepository() throws IOException {
        Sensor newSensor = new Sensor(SENSOR_LOCATION, 100.0);
        Sensor existingSensor = new Sensor(SENSOR_LOCATION, 50.0);
        when(repository.findById(SENSOR_LOCATION)).thenReturn(Optional.of(existingSensor));
        setupAndTestSensor(newSensor, true);
        verify(repository, times(1)).findById(SENSOR_LOCATION);
        verify(repository, times(1)).save(any(Sensor.class));
    }

    private void setupAndTestSensor(Sensor sensor, boolean shouldReceive) throws IOException {
        when(repository.save(sensor)).thenAnswer(invocation -> invocation.getArgument(0));
        producer.send(new GenericMessage<>(sensor), CONSUMER_BINDING_NAME);
        Message<byte[]> receivedMessage = consumer.receive(1000, TOPIC_NAME);
        if (shouldReceive) {
            assertNotNull("Message not received", receivedMessage);
            Sensor receivedSensor = objectMapper.readValue(receivedMessage.getPayload(), Sensor.class);
            assertEquals("Invalid sensor coordinates", SENSOR_LOCATION, receivedSensor.spotCoordinates());
            assertEquals("Invalid sensor value", 100.0, receivedSensor.quantity());
        } else {
            assertNull("Message should not be received", receivedMessage);
        }
    }
}
