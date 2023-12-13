package com.karmiel.create.order.record.service.repo;

import com.karmiel.create.order.record.service.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersRecordsRepo extends MongoRepository<Order, String> {
    Boolean existsBySpotCoordinatesAndOrderStatusIn(String spotCoordinates, String[] statuses);
}
