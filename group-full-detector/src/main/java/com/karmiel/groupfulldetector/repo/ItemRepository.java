package com.karmiel.groupfulldetector.repo;

import com.karmiel.groupfulldetector.dto.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ItemRepository extends MongoRepository<OrderItem, Integer> {
    @Query(value="{spotCoordinates: '?0'}", fields = "{'id' :  1, 'orderStatus': 1}")
    List<OrderItem> findAllOrdersBySpotCoordinates (String spotCoordinates);

    @Query("{id: '?0'}")
    OrderItem findItemByOrderId (long id);

    public long count();



}
