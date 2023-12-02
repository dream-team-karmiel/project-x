package com.karmiel.grouplackdetector.service;

import com.karmiel.grouplackdetector.dto.ContainerData;
import com.karmiel.grouplackdetector.dto.Sensor;

public interface GroupLackDetectorService {

    void detect(Sensor sensor);

    ContainerData getContainerCapacity(String containerId);

    Boolean isGood(Sensor sensor, ContainerData data);

    Double calculateRequiredQuantity(ContainerData data, Double quantity);

    void sendNewOrder(String spotCoordinates, String productName, Double requiredQuantity);

    void sendCheckOpenOrder(String spotCoordinates);
}
