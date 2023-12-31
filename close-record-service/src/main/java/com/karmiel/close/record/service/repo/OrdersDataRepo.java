package com.karmiel.close.record.service.repo;

import com.karmiel.close.record.service.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersDataRepo extends MongoRepository<Order, String> {
}
