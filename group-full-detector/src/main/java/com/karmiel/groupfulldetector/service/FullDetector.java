package com.karmiel.groupfulldetector.service;

import com.karmiel.groupfulldetector.dto.FullData;

public interface FullDetector {
    String checkOrder(FullData data);
}
