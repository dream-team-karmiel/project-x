package com.karmiel.create.order.record.service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.create.order.record.service.dto.NewOrder;
import com.karmiel.create.order.record.service.entities.OrderStatus;
import com.karmiel.create.order.record.service.repo.OrdersRecordsRepo;
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
import java.util.EnumSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class CreateOrderRecordServiceTest {
    @Autowired(required = false)
    InputDestination producer;

    @Autowired(required = false)
    OutputDestination consumer;

    @MockBean
    OrdersRecordsRepo repo;

    private final ObjectMapper mapper = new ObjectMapper();
    private final String consumerBindingName = "new-required-order";
    private final String producerBindingName = "save-order";
    private final String SPOT_COORDINATE = "A01";
    private final Set<OrderStatus> statuses = EnumSet.of(OrderStatus.NEW, OrderStatus.CONFIRMED);
    private final NewOrder newOrder = new NewOrder(SPOT_COORDINATE, "water", 42.);

    @Test
    void orderAlreadyPresentTest() {
        doReturn(true)
                .when(repo).existsBySpotCoordinatesAndOrderStatusIn(SPOT_COORDINATE, statuses);

        producer.send(new GenericMessage<>(newOrder), consumerBindingName);
        Message<byte[]> noAnswer = consumer.receive(100, producerBindingName);

        assertNull(noAnswer);
    }

    @Test
    void orderNotYetPresentTest() throws IOException {
        doReturn(false)
                .when(repo).existsBySpotCoordinatesAndOrderStatusIn(SPOT_COORDINATE, statuses);

        producer.send(new GenericMessage<>(newOrder), consumerBindingName);
        Message<byte[]> answer = consumer.receive(100, producerBindingName);

        assertNotNull(answer);
        assertEquals(newOrder, mapper.readValue(answer.getPayload(), NewOrder.class));
    }
}