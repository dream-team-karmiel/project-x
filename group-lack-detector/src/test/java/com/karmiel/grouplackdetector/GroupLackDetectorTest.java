package com.karmiel.grouplackdetector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.grouplackdetector.dto.ContainerData;
import com.karmiel.grouplackdetector.dto.NewOrder;
import com.karmiel.grouplackdetector.dto.Sensor;
import com.karmiel.grouplackdetector.dto.SpotCoordinates;
import com.karmiel.grouplackdetector.repository.ContainerRepository;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class GroupLackDetectorTest {

    @Autowired
    InputDestination producer;
    @Autowired
    OutputDestination consumer;
    @MockBean
    ContainerRepository repository;

    private final String producerBindingName = "receiveSensorData-in-0";

    @Test
    void sendNewOrderWhenQuantityLessLimit() throws Exception {
        Sensor sensor = new Sensor("A10", 49.);
        String id = sensor.spotCoordinates();
        String product = "Water";
        ContainerData data = new ContainerData(id, product, 100.);

        when(repository.findDataById(id)).thenReturn(Optional.of(data));

        producer.send(new GenericMessage<>(sensor), producerBindingName);

        String newOrderConsumerBindingName = "topic-1-1";
        Message<byte[]> message = consumer.receive(100, newOrderConsumerBindingName);
        assertNotNull(message);
        ObjectMapper mapper = new ObjectMapper();
        NewOrder order = new NewOrder(id, product, 51.);
        assertEquals(order, mapper.readValue(message.getPayload(), NewOrder.class));
    }

    @Test
    void sendCheckOpenOrderWhenQuantityAboveLimit() throws Exception {
        Sensor sensor = new Sensor("A10", 51.);
        String spotCoordinates = sensor.spotCoordinates();
        String product = "Water";
        ContainerData data = new ContainerData(spotCoordinates, product, 100.);

        when(repository.findDataById(spotCoordinates)).thenReturn(Optional.of(data));

        producer.send(new GenericMessage<>(sensor), producerBindingName);

        String checkOrderConsumerBindingName = "topic-1-2";
        Message<byte[]> message = consumer.receive(100, checkOrderConsumerBindingName);
        assertNotNull(message);
        ObjectMapper mapper = new ObjectMapper();
        SpotCoordinates coordinates = new SpotCoordinates(spotCoordinates);
        assertEquals(coordinates, mapper.readValue(message.getPayload(), SpotCoordinates.class));
    }

    // TODO: Test then quantity above capacity
}