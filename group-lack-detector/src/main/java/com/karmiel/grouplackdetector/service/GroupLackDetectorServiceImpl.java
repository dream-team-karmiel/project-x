package com.karmiel.grouplackdetector.service;

import com.karmiel.grouplackdetector.dto.ContainerData;
import com.karmiel.grouplackdetector.dto.NewOrder;
import com.karmiel.grouplackdetector.dto.Sensor;
import com.karmiel.grouplackdetector.dto.SpotCoordinates;
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

    @Value("${app.group-lack-detector.binding.new-order:topic-1-1}")
    String newOrder;

    @Value("${app.group-lack-detector.binding.check-open-order:topic-1-2}")
    String checkOpenOrder;

    @Override
    public void detect(Sensor sensor) {
        ContainerData data = getContainerCapacity(sensor.spotCoordinates());
        if (isGood(sensor, data)) {
            sendCheckOpenOrder(sensor.spotCoordinates());
        } else {
            Double requiredQuantity = calculateRequiredQuantity(data, sensor.quantity());
            sendNewOrder(sensor.spotCoordinates(), data.productName(), requiredQuantity);
        }
    }

    @Override
    public ContainerData getContainerCapacity(String containerId) {
        // TODO: Подключение запрос к PostgreSQL
        return repository.findDataById(containerId).orElseThrow(() ->
                new NoSuchElementException(String.format("Container with id %s does not exists", containerId)));
    }

    @Override
    public Boolean isGood(Sensor sensor, ContainerData data) {
        return sensor.quantity() / data.capacity() > limit;
    }

    @Override
    public Double calculateRequiredQuantity(ContainerData data, Double quantity) {
        // TODO: Логика вычисления рекумендуемого объема заказа
        return data.capacity() - quantity;
    }

    @Override
    public void sendNewOrder(String spotCoordinates, String productName, Double requiredQuantity) {
        NewOrder order = new NewOrder(spotCoordinates, productName, requiredQuantity);
        streamBridge.send(newOrder, order);
        log.trace("Message to create new order for container {} has been sent", spotCoordinates);
    }

    @Override
    public void sendCheckOpenOrder(String spotCoordinates) {
        SpotCoordinates coordinates = new SpotCoordinates(spotCoordinates);
        streamBridge.send(checkOpenOrder, coordinates);
        log.debug("Message to check the order for container {} has been sent", coordinates.spotCoordinates());
    }
}
