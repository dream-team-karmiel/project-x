package com.karmiel.grouplackdetector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.grouplackdetector.dto.NewOrder;
import com.karmiel.grouplackdetector.dto.Sensor;
import com.karmiel.grouplackdetector.dto.SpotCoordinates;
import com.karmiel.grouplackdetector.model.Container;
import com.karmiel.grouplackdetector.model.Measure;
import com.karmiel.grouplackdetector.model.Product;
import com.karmiel.grouplackdetector.repository.ContainerRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
@Import(TestChannelBinderConfiguration.class)
class GroupLackDetectorTest {

    @Resource
    InputDestination producer;
    @Autowired(required = false)
    OutputDestination consumer;
    @MockBean
    ContainerRepository repository;

    private final String producerBindingName = "receiveSensorData-in-0";
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void sendNewOrderWhenQuantityLessLimit() throws Exception {

        String id = "A10";
        Sensor sensor = new Sensor(id, 49.);

        String measureName = "Liter";
        Measure measure = new Measure(1L, measureName);

        String productName = "Water";
        Product product = new Product(1L, productName, measure, 100.);

        Container container = new Container(id, product);

        when(repository.findById(id)).thenReturn(Optional.of(container));

        producer.send(new GenericMessage<>(sensor), producerBindingName);

        String newOrderConsumerBindingName = "topic-1-1";
        Message<byte[]> message = consumer.receive(100, newOrderConsumerBindingName);
        assertNotNull(message);
        NewOrder order = new NewOrder(id, productName, 51.);
        assertEquals(order, mapper.readValue(message.getPayload(), NewOrder.class));
    }

    @Test
    void sendCheckOpenOrderWhenQuantityAboveLimit() throws Exception {

        String id = "A10";
        Sensor sensor = new Sensor(id, 51.);

        String measureName = "Liter";
        Measure measure = new Measure(1L, measureName);

        String productName = "Water";
        Product product = new Product(1L, productName, measure, 100.);

        Container container = new Container(id, product);

        when(repository.findById(id)).thenReturn(Optional.of(container));

        producer.send(new GenericMessage<>(sensor), producerBindingName);

        String checkOrderConsumerBindingName = "topic-1-2";
        Message<byte[]> message = consumer.receive(100, checkOrderConsumerBindingName);
        assertNotNull(message);
        SpotCoordinates coordinates = new SpotCoordinates(id);
        assertEquals(coordinates, mapper.readValue(message.getPayload(), SpotCoordinates.class));
    }

}