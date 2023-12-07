package com.karmiel.grouplackdetector.service;

import com.karmiel.grouplackdetector.dto.NewOrder;
import com.karmiel.grouplackdetector.dto.Sensor;
import com.karmiel.grouplackdetector.dto.SpotCoordinates;
import com.karmiel.grouplackdetector.model.Container;
import com.karmiel.grouplackdetector.repository.ContainerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupLackDetectorServiceImpl implements GroupLackDetectorService {

    private final StreamBridge streamBridge;

    private final ContainerRepository repository;

    @Value("${app.group-lack-detector.limit:0.5}")
    private double limit;

    @Value("${spring.cloud.stream.bindings.topic-1-1.destination:topic-1-1}")
    private String newOrderTopic;

    @Value("${spring.cloud.stream.bindings.topic-1-2.destination:topic-1-2}")
    private String checkOpenOrder;

    @Override
    public void detect(Sensor sensor) {
        Container container = getContainerCapacity(sensor.spotCoordinates());
        if (isGood(sensor, container)) {
            sendCheckOpenOrder(sensor.spotCoordinates());
        } else {
            Double requiredQuantity = calculateRequiredQuantity(container, sensor.quantity());
            sendNewOrder(sensor.spotCoordinates(), container.getProduct().getProductName(), requiredQuantity);
        }
    }

    @Override
    public Container getContainerCapacity(String containerId) {
        return repository.findById(containerId).orElseThrow(() ->
                new NoSuchElementException(String.format("Container with id %s does not exists", containerId)));
    }

    @Override
    public Boolean isGood(Sensor sensor, Container container) {
        return sensor.quantity() / container.getProduct().getCapacity() > limit;
    }

    @Override
    public Double calculateRequiredQuantity(Container container, Double quantity) {
        return container.getProduct().getCapacity() - quantity;
    }

    @Override
    public void sendNewOrder(String spotCoordinates, String productName, Double requiredQuantity) {
        NewOrder order = new NewOrder(spotCoordinates, productName, requiredQuantity);
        streamBridge.send(newOrderTopic, order);
        log.trace("Message to create new order for container {} has been sent", spotCoordinates);
    }

    @Override
    public void sendCheckOpenOrder(String spotCoordinates) {
        SpotCoordinates coordinates = new SpotCoordinates(spotCoordinates);
        streamBridge.send(checkOpenOrder, coordinates);
        log.debug("Message to check the order for container {} has been sent", coordinates.spotCoordinates());
    }
}
