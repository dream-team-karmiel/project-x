package com.karmiel.groupfulldetector.service;

import com.karmiel.groupfulldetector.dto.OrderItem;

public interface FullDetector {
    String fullDetection(OrderItem orderItem);
}
