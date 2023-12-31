package com.karmiel.groupfulldetector.service;

import com.karmiel.groupfulldetector.dto.FullData;
import com.karmiel.groupfulldetector.dto.Order;
import com.karmiel.groupfulldetector.enities.OrderData;
import com.karmiel.groupfulldetector.repo.ItemRepository;
import com.karmiel.groupfulldetector.utils.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class FullDetectorImpl implements FullDetector{
    private final ItemRepository repository;
    private final StreamBridge streamBridge;
    @Value("${spring.cloud.stream.bindings.full-out-0.destination:close-order}")
    String closeOrderTopic;

//    @Cacheable("checkOrder")
    @Override
    public void orderStatusProcessing(FullData data) {
        String spotCoordinates = data.spotCoordinates();
        Set<OrderStatus> statuses = Set.of(OrderStatus.CONFIRMED);
        List<OrderData> orders = repository.findBySpotCoordinatesAndOrderStatusIn(spotCoordinates, statuses);
        orders.forEach(o -> {
            streamBridge.send(closeOrderTopic, new Order(o.getId()));
            log.trace("Send message Order {} for container {} should be close", o.getId(), o.getSpotCoordinates());
                }
        );
    }
}