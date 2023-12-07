package com.karmiel.grouplackdetector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.grouplackdetector.dto.NewOrder;
import com.karmiel.grouplackdetector.dto.Sensor;
import com.karmiel.grouplackdetector.dto.SpotCoordinates;
import com.karmiel.grouplackdetector.model.Container;
import com.karmiel.grouplackdetector.model.Measures;
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

    @Test
    void sendNewOrderWhenQuantityLessLimit() throws Exception {

        String id = "A10";
        Sensor sensor = new Sensor(id, 49.);

        Measures pack = new Measures();
        pack.setId(1L);
        pack.setMeasureName("Liter");

        Product product = new Product();
        product.setId(1L);
        String productName = "Water";
        product.setProductName(productName);
        product.setMeasure(pack);
        product.setCapacity(100.);

        Container container = new Container();
        container.setSpotCoordinates(id);
        container.setProduct(product);

        when(repository.findById(id)).thenReturn(Optional.of(container));

        producer.send(new GenericMessage<>(sensor), producerBindingName);

        String newOrderConsumerBindingName = "topic-1-1";
        Message<byte[]> message = consumer.receive(100, newOrderConsumerBindingName);
        assertNotNull(message);
        ObjectMapper mapper = new ObjectMapper();
        NewOrder order = new NewOrder(id, productName, 51.);
        assertEquals(order, mapper.readValue(message.getPayload(), NewOrder.class));
    }

    @Test
    void sendCheckOpenOrderWhenQuantityAboveLimit() throws Exception {

        String id = "A10";
        Sensor sensor = new Sensor(id, 51.);

        Measures pack = new Measures();
        pack.setId(1L);
        pack.setMeasureName("Liter");

        Product product = new Product();
        product.setId(1L);
        String productName = "Water";
        product.setProductName(productName);
        product.setMeasure(pack);
        product.setCapacity(100.);

        Container container = new Container();
        container.setSpotCoordinates(id);
        container.setProduct(product);

        when(repository.findById(id)).thenReturn(Optional.of(container));

        producer.send(new GenericMessage<>(sensor), producerBindingName);

        String checkOrderConsumerBindingName = "topic-1-2";
        Message<byte[]> message = consumer.receive(100, checkOrderConsumerBindingName);
        assertNotNull(message);
        ObjectMapper mapper = new ObjectMapper();
        SpotCoordinates coordinates = new SpotCoordinates(id);
        assertEquals(coordinates, mapper.readValue(message.getPayload(), SpotCoordinates.class));
    }

}