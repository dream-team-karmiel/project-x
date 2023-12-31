package com.karmiel.groupfulldetector.service;

import com.karmiel.groupfulldetector.dto.FullData;

public interface FullDetector {
    void orderStatusProcessing(FullData data);
}
