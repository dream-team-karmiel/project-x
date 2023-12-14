package com.karmiel.groupfulldetector.service;

import com.karmiel.groupfulldetector.dto.FullData;
import com.karmiel.groupfulldetector.enities.OrderData;
import com.karmiel.groupfulldetector.repo.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FullDetectorImpl implements FullDetector{
    final ItemRepository itemRepository;
    @Value("close-order")
    String closeOrderTopic;
    @Cacheable("checkOrder")
    @Override
    public String checkOrder(FullData data) {
        String containerId = data.spotCoordinates();
        Optional<OrderData> orderData = itemRepository.findOrderBySpotCoordinatesAndStatus(containerId);
        return orderData.map(OrderData::getId).orElse(null);
    }
}