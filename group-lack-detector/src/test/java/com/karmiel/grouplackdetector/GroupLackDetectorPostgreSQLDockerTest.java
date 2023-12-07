package com.karmiel.grouplackdetector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.grouplackdetector.dto.NewOrder;
import com.karmiel.grouplackdetector.dto.Sensor;
import com.karmiel.grouplackdetector.dto.SpotCoordinates;
import com.karmiel.grouplackdetector.repository.ContainerRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RequiredArgsConstructor
@Import(TestChannelBinderConfiguration.class)
class GroupLackDetectorPostgreSQLDockerTest {

    @Resource
    InputDestination producer;
    @Autowired(required = false)
    OutputDestination consumer;
    @Autowired
    ContainerRepository repository;

    private final String spotCoordinates = "A10";

    private final String producerBindingName = "receiveSensorData-in-0";

    @Test
    void sendNewOrderWhenQuantityLessLimit() throws Exception {

        Sensor sensor = new Sensor(spotCoordinates, 49.);

        producer.send(new GenericMessage<>(sensor), producerBindingName);

        String newOrderConsumerBindingName = "topic-1-1";
        Message<byte[]> message = consumer.receive(100, newOrderConsumerBindingName);
        assertNotNull(message);
        String product = "Water";
        NewOrder order = new NewOrder(spotCoordinates, product, 51.);
        ObjectMapper mapper = new ObjectMapper();
        assertEquals(order, mapper.readValue(message.getPayload(), NewOrder.class));
    }

    @Test
    void sendCheckOpenOrderWhenQuantityAboveLimit() throws Exception {
        Sensor sensor = new Sensor(spotCoordinates, 51.);

        producer.send(new GenericMessage<>(sensor), producerBindingName);

        String checkOrderConsumerBindingName = "topic-1-2";
        Message<byte[]> message = consumer.receive(100, checkOrderConsumerBindingName);
        assertNotNull(message);
        ObjectMapper mapper = new ObjectMapper();
        SpotCoordinates coordinates = new SpotCoordinates(spotCoordinates);
        assertEquals(coordinates, mapper.readValue(message.getPayload(), SpotCoordinates.class));
    }

}