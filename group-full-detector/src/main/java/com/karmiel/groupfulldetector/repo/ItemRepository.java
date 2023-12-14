package com.karmiel.groupfulldetector.repo;

import com.karmiel.groupfulldetector.enities.OrderData;
import com.karmiel.groupfulldetector.utils.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ItemRepository extends MongoRepository<OrderData, String> {
    @Query(value="{spotCoordinates: '?0', orderStatus: OrderStatus.CONFIRMED}", fields = "{'id' :  1, 'spotCoordinates' : 1, 'orderStatus': 1}")
    Optional<OrderData> findOrderBySpotCoordinatesAndStatus (String spotCoordinates);
}
