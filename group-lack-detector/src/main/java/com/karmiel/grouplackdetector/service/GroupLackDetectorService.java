package com.karmiel.grouplackdetector.service;

import com.karmiel.grouplackdetector.dto.Sensor;
import com.karmiel.grouplackdetector.model.Container;

public interface GroupLackDetectorService {

    void detect(Sensor sensor);

    Container getContainerCapacity(String containerId);

    Boolean isGood(Sensor sensor, Container container);

    Double calculateRequiredQuantity(Container container, Double quantity);

    void sendNewOrder(String spotCoordinates, String productName, Double requiredQuantity);

    void sendCheckOpenOrder(String spotCoordinates);
}
