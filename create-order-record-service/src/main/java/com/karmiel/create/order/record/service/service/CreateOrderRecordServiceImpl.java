package com.karmiel.create.order.record.service.service;

import com.karmiel.create.order.record.service.dto.NewOrder;
import com.karmiel.create.order.record.service.entities.OrderStatus;
import com.karmiel.create.order.record.service.repo.OrdersRecordsRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateOrderRecordServiceImpl implements CreateOrderRecordService {
    private final StreamBridge streamBridge;
    private final OrdersRecordsRepo repository;

    @Value("${spring.cloud.stream.bindings.sendNewOrder-out-0.destination:save-order}")
    private String bindingName;

    @Override
    public void createOrderRecord(NewOrder newOrder) {
        Set<OrderStatus> statuses = EnumSet.of(OrderStatus.NEW, OrderStatus.CONFIRMED);
        String spotCoordinates = newOrder.spotCoordinates();
        Boolean isOrderExists = repository.existsBySpotCoordinatesAndOrderStatusIn(spotCoordinates, statuses);
        if (!isOrderExists) {
            streamBridge.send(bindingName, newOrder);
            log.trace("Order for container with spotCoordinates = {} send to topic = {}", spotCoordinates, bindingName);
        } else {
            log.trace("Order for container with spotCoordinates = {} exists", spotCoordinates);
        }
    }
}
