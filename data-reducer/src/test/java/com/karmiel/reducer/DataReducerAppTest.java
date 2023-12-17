package com.karmiel.reducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.reducer.repository.SensorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
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
        when(repository.save(newSensor)).thenAnswer(invocation -> invocation.getArgument(0));

        producer.send(new GenericMessage<>(newSensor), CONSUMER_BINDING_NAME);
        Message<byte[]> receivedMessage = consumer.receive(1000, TOPIC_NAME);

        assertNotNull("Message not received", receivedMessage);
        Sensor receivedSensor = objectMapper.readValue(receivedMessage.getPayload(), Sensor.class);
        assertEquals("Invalid sensor coordinates", SENSOR_LOCATION, receivedSensor.spotCoordinates());
        assertEquals("Invalid sensor value", 100.0, receivedSensor.quantity());
        verify(repository, times(1)).save(newSensor);
    }

    @Test
    public void shouldIgnoreSensorDataIfSensorAlreadyExistsInRepository() throws IOException {
        Sensor existingSensor = new Sensor(SENSOR_LOCATION, 100.0);
        when(repository.findById(SENSOR_LOCATION)).thenReturn(Optional.of(existingSensor));

        producer.send(new GenericMessage<>(existingSensor), CONSUMER_BINDING_NAME);
        Message<byte[]> receivedMessage = consumer.receive(1000, TOPIC_NAME);

        assertNull("Message should not be received", receivedMessage);
        verify(repository, never()).save(any(Sensor.class));
    }

    @Test
    public void shouldReceiveAndSaveSensorDataWithUpdatedQuantityIfExistsInRepository() throws IOException {

        Sensor newSensor = new Sensor(SENSOR_LOCATION, 100.0);
        Sensor existingSensor = new Sensor(SENSOR_LOCATION, 50.0);
        when(repository.findById(SENSOR_LOCATION)).thenReturn(Optional.of(existingSensor));
        when(repository.save(any(Sensor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        producer.send(new GenericMessage<>(newSensor), CONSUMER_BINDING_NAME);
        Message<byte[]> receivedMessage = consumer.receive(1000, TOPIC_NAME);

        assertNotNull("Message not received", receivedMessage);
        Sensor receivedSensor = objectMapper.readValue(receivedMessage.getPayload(), Sensor.class);
        assertEquals("Invalid sensor coordinates", SENSOR_LOCATION, receivedSensor.spotCoordinates());
        assertEquals("Invalid sensor value", 100.0, receivedSensor.quantity());
        verify(repository, times(1)).save(any(Sensor.class));
    }
}
