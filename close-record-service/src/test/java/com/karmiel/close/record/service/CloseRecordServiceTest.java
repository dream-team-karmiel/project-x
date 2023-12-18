package com.karmiel.close.record.service;

import com.karmiel.close.record.service.dto.CloseRecordDto;
import com.karmiel.close.record.service.entities.Order;
import com.karmiel.close.record.service.enums.OrderStatus;
import com.karmiel.close.record.service.repo.OrdersDataRepo;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class CloseRecordServiceTest {
    private static final String ID_ORDER = "107";
    private static final String NO_ORDER_ID = " ";
    private static final String BINDING_NAME = "orderDtoConsumer-in-0";
    @Resource
    InputDestination producer;

    @MockBean
    OrdersDataRepo OrdersRepo;

    static Order order = new Order("123", ID_ORDER, LocalDateTime.now(), LocalDateTime.now(), "TestProductName", 12.3, OrderStatus.CONFIRMED);
    static Order updateOrder = new Order("123", ID_ORDER, LocalDateTime.now(), LocalDateTime.now(), "TestProductName", 12.3, OrderStatus.DONE);
    static CloseRecordDto recordOrder = new CloseRecordDto(ID_ORDER);
    static CloseRecordDto recordOrderNoId = new CloseRecordDto(NO_ORDER_ID);

    @Test
    void closeNoOrderTest() {
        when(OrdersRepo.findById(NO_ORDER_ID))
                .thenReturn(Optional.empty());
        producer.send(new GenericMessage<>(recordOrderNoId), BINDING_NAME);
        verify(OrdersRepo, times(0)).save(any(Order.class));
    }

    @Test
    void closeOrderTest() {
        when(OrdersRepo.findById(ID_ORDER))
                .thenReturn(Optional.of(order));
        when(OrdersRepo.save(updateOrder))
                .thenAnswer(invocation -> {
                    Order savedOrder = invocation.getArgument(0);
                    savedOrder.setOrderStatus(OrderStatus.DONE);
                    savedOrder.setCloseDate(LocalDateTime.now());
                    return savedOrder;
                });

        producer.send(new GenericMessage<>(recordOrder), BINDING_NAME);
        verify(OrdersRepo, times(1)).save(any(Order.class));
    }
}