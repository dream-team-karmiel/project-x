package com.karmiel.groupfulldetector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.groupfulldetector.dto.FullData;
import com.karmiel.groupfulldetector.dto.Order;
import com.karmiel.groupfulldetector.enities.OrderData;
import com.karmiel.groupfulldetector.repo.ItemRepository;
import com.karmiel.groupfulldetector.utils.OrderStatus;
import org.junit.jupiter.api.Disabled;
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
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class GroupFullDetectorTest {

    @Autowired
    InputDestination producer;
    @Autowired
    OutputDestination consumer;
    @MockBean
    ItemRepository repository;

    String producerBindingName = "receiveFullData-in-0";
   String consumerBindingName = "close-order";

    @Test
    @Disabled
    @DirtiesContext
    void sendRequestToCloseOrder() throws Exception{
        String containerId = "A11";
        FullData fullData = new FullData(containerId);
        OrderData order = new OrderData();
        order.setId("348");
        order.setSpotCoordinates(containerId);
        order.setOrderStatus(OrderStatus.CONFIRMED);

        when(repository.findBySpotCoordinatesAndOrderStatusIn(containerId, Set.of(OrderStatus.CONFIRMED))).thenReturn(List.of(order));

        producer.send(new GenericMessage<>(fullData), producerBindingName);

        Message<byte[]> message = consumer.receive(100, consumerBindingName);

        assertNotNull(message);
        ObjectMapper mapper = new ObjectMapper();
        assertEquals(order.getId(), mapper.readValue(message.getPayload(), Order.class).id());
    }

    @Test
    @Disabled
    @DirtiesContext
    void notSendRequestToCloseOrder() throws Exception{
        String containerId = "A11";
        FullData fullData = new FullData(containerId);

        when(repository.findBySpotCoordinatesAndOrderStatusIn(containerId, Set.of(OrderStatus.CONFIRMED))).thenReturn(new ArrayList<>());

        producer.send(new GenericMessage<>(fullData), producerBindingName);

        Message<byte[]> message = consumer.receive(100, consumerBindingName);
        assertNull(message);
    }
}