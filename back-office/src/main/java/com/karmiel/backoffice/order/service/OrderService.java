package com.karmiel.backoffice.order.service;

import com.karmiel.backoffice.order.dto.OrderCreateRequestDto;
import com.karmiel.backoffice.order.dto.OrderDto;
import com.karmiel.backoffice.order.dto.OrderUpdateRequestDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getOrders();

    OrderDto getOrderById(String id);

    OrderDto createOrder(OrderCreateRequestDto requestDto);

    OrderDto updateOrder(OrderUpdateRequestDto requestDto);
}
