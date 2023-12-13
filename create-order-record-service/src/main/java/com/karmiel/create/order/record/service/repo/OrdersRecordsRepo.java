package com.karmiel.create.order.record.service.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersRecordsRepo extends MongoRepository<Object, String> {
}
