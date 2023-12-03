package com.karmiel.groupfulldetector.service;

import com.karmiel.groupfulldetector.dto.OrderItem;
import com.karmiel.groupfulldetector.repo.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FullDetectorImpl implements FullDetector{
    final ItemRepository itemRepository;

    @Override
    public String fullDetection(OrderItem orderItem) {
        long id = orderItem.getId();
        String spotCoordinates = orderItem.getSpotCoordinates();
        String orderStatus = orderItem.getOrderStatus();


        return null;
    }
}
