package com.karmiel.groupfulldetector.service;

import com.karmiel.groupfulldetector.dto.FullData;
import com.karmiel.groupfulldetector.enities.OrderData;
import com.karmiel.groupfulldetector.repo.ItemRepository;
import com.karmiel.groupfulldetector.utils.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FullDetectorImpl implements FullDetector{
    final ItemRepository itemRepository;
    @Cacheable("checkOrder")
    @Override
    public String checkOrder(FullData data) {
        String containerId = data.spotCoordinates();
        Optional<OrderData> orderData = itemRepository.findOrderBySpotCoordinatesAndStatus(containerId, OrderStatus.CONFIRMED);
        return orderData.map(OrderData::getId).orElse(null);
    }
}