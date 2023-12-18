package com.karmiel.create.order.record.service.repo;

import com.karmiel.create.order.record.service.entities.Order;
import com.karmiel.create.order.record.service.entities.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

public interface OrdersRecordsRepo extends MongoRepository<Order, String> {
    Boolean existsBySpotCoordinatesAndOrderStatusIn(String spotCoordinates, Set<OrderStatus> orderStatus);
}
