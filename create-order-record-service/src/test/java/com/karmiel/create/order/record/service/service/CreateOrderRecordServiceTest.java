package com.karmiel.create.order.record.service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.create.order.record.service.dto.NewOrder;
import com.karmiel.create.order.record.service.entities.OrderStatus;
import com.karmiel.create.order.record.service.repo.OrdersRecordsRepo;
import org.junit.jupiter.api.BeforeAll;
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
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class CreateOrderRecordServiceTest {
    @Autowired(required = false)
    InputDestination producer;

    @Autowired(required = false)
    OutputDestination consumer;

    @MockBean
    OrdersRecordsRepo repo;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final String consumerBindingName = "new-required-order";
    private final String producerBindingName = "save-order";

    private static final String SPOT_COORDINATE_ALREADY_PRESENTED = "A01";
    private static final String SPOT_COORDINATE_NOT_YET_PRESENTED = "A02";

    private static final Set<OrderStatus> statuses = EnumSet.of(OrderStatus.NEW, OrderStatus.CONFIRMED);

    private static NewOrder newOrderAlreadyPresented;
    private static NewOrder newOrderNotYetPresented;

    @BeforeAll
    static void setUpAll() {
        newOrderAlreadyPresented = new NewOrder(
                SPOT_COORDINATE_ALREADY_PRESENTED,
                "product-1",
                .2);
        newOrderNotYetPresented = new NewOrder(
                SPOT_COORDINATE_NOT_YET_PRESENTED,
                "product-2",
                .2);
    }

    @Test
    void orderAlreadyPresentTest() {
        when(repo.existsBySpotCoordinatesAndOrderStatusIn(SPOT_COORDINATE_ALREADY_PRESENTED, statuses))
                .thenReturn(true);

        producer.send(new GenericMessage<>(newOrderAlreadyPresented), consumerBindingName);
        Message<byte[]> noAnswer = consumer.receive(100, producerBindingName);

        assertNull(noAnswer);
    }

    @Test
    void orderNotYetPresentTest() throws IOException {
        when(repo.existsBySpotCoordinatesAndOrderStatusIn(SPOT_COORDINATE_ALREADY_PRESENTED, statuses))
                .thenReturn(false);

        producer.send(new GenericMessage<>(newOrderNotYetPresented), consumerBindingName);
        Message<byte[]> answer = consumer.receive(100, producerBindingName);

        assertNotNull(answer);
        assertEquals(newOrderNotYetPresented, MAPPER.readValue(answer.getPayload(), NewOrder.class));
    }
}