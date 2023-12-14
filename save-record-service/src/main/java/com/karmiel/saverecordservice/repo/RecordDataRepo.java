package com.karmiel.saverecordservice.repo;

import com.karmiel.saverecordservice.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecordDataRepo extends MongoRepository<Order, String> {
}