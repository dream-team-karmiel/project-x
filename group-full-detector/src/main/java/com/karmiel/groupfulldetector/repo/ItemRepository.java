package com.karmiel.groupfulldetector.repo;

import com.karmiel.groupfulldetector.enities.OrderData;
import com.karmiel.groupfulldetector.utils.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface ItemRepository extends MongoRepository<OrderData, String> {

    List<OrderData> findBySpotCoordinatesAndOrderStatusIn (String spotCoordinates, Set<OrderStatus> status);
}
