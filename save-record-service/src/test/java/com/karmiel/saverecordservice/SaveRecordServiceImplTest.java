package com.karmiel.saverecordservice;

import com.karmiel.saverecordservice.dto.NewOrder;
import com.karmiel.saverecordservice.dto.OrderStatus;
import com.karmiel.saverecordservice.entities.Order;
import com.karmiel.saverecordservice.repo.RecordDataRepo;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.messaging.support.GenericMessage;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@SpringBootTest
class SaveRecordServiceImplTest {
    @Resource
    InputDestination producer;
    @MockBean
    RecordDataRepo recordDataRepo;

    String bindingName = "receiveRecordData-in-0";

    @Test
    void saveRecordDataTest() {
        // 1
        String spotCoordinates = "A10";
        String productName = "Beer";
        Double requiredQuantity = 51.;

        NewOrder newOrder = new NewOrder(spotCoordinates, productName, requiredQuantity);

        Order order = new Order("qwe123", spotCoordinates, LocalDateTime.now(), null, productName,
                requiredQuantity, OrderStatus.NEW);

        when(recordDataRepo.save(any(Order.class))).thenReturn(order);

        // 2

        producer.send(new GenericMessage<>(newOrder), bindingName);

        // 3
        verify(recordDataRepo, times(1)).save(any(Order.class));
    }
}

