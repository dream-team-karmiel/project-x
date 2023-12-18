package com.karmiel.create.order.record.service.controller;

import com.karmiel.create.order.record.service.dto.NewOrder;
import com.karmiel.create.order.record.service.service.CreateOrderRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Controller
@RequiredArgsConstructor
public class CreateOrderRecordController {
    private final CreateOrderRecordService service;

    @Bean
    Consumer<NewOrder> recieveNewOrder() {
        return service::createOrderRecord;
    }
}
