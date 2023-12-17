package com.karmiel.saverecordservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.saverecordservice.dto.NewOrder;
import com.karmiel.saverecordservice.dto.OrderStatus;
import com.karmiel.saverecordservice.entities.Order;
import com.karmiel.saverecordservice.repo.RecordDataRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaveRecordServiceImpl implements SaveRecordService {
    final RecordDataRepo recordDataRepo;
    ObjectMapper mapper = new ObjectMapper();

    @Bean
    @Override
    public Consumer<String> saveRecordData() {
        return recordData -> {
            if (recordData != null) {
                try {
                    NewOrder newOrder = mapper.readValue(recordData, NewOrder.class);
                    LocalDateTime createDate = LocalDateTime.now();
                    Order order = new Order();
                    order.setSpotCoordinates(newOrder.spotCoordinates());
                    order.setProductName(newOrder.productName());
                    order.setRequiredQuantity(newOrder.requiredQuantity());
                    order.setCreationDate(createDate);
                    order.setOrderStatus(OrderStatus.NEW);
                    Order savedOrder = recordDataRepo.save(order);
                    log.trace("Order {} saved", savedOrder);
                } catch (Exception e) {
                    log.trace(e.getMessage());
                }
            }
        };
    }
}