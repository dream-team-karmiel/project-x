package com.karmiel.backoffice.order.service;

import com.karmiel.backoffice.exception.NotValidStatusException;
import com.karmiel.backoffice.exception.ResourceNotFoundException;
import com.karmiel.backoffice.order.common.OrderStatus;
import com.karmiel.backoffice.order.dto.OrderCreateRequestDto;
import com.karmiel.backoffice.order.dto.OrderDto;
import com.karmiel.backoffice.order.dto.OrderMapper;
import com.karmiel.backoffice.order.dto.OrderUpdateRequestDto;
import com.karmiel.backoffice.order.model.Order;
import com.karmiel.backoffice.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    @Override
    public List<OrderDto> getOrders() {
        List<Order> orders = repository.findAll();
        log.trace("Orders are received from the repository");
        return mapper.toOrderDto(orders);
    }

    @Override
    public OrderDto getOrderById(String id) {
        Order order = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Order with id %s does not exist", id)));
        log.trace("Order received from repository");
        return mapper.toOrderDto(order);
    }

    @Override
    public OrderDto createOrder(OrderCreateRequestDto requestDto) {
        Order order = mapper.toOrder(requestDto);
        Order savedOrder = repository.save(order);
        log.trace("Order created with id {}", savedOrder.getId());
        return mapper.toOrderDto(savedOrder);
    }

    @Override
    public OrderDto updateOrder(OrderUpdateRequestDto requestDto) {
        String id = requestDto.id();
        Order order = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Order with id %s not found", id)));
        if (order.getCloseDate() != null) {
            throw new NotValidStatusException(String.format("Order with id %s is already closed", id));
        }
        if (requestDto.orderStatus().equals(OrderStatus.DONE)) {
            order.setOrderStatus(OrderStatus.DONE);
        }
        order.setRequiredQuantity(requestDto.requiredQuantity());
        order.setOrderStatus(requestDto.orderStatus());
        Order savedOrder = repository.save(order);
        return mapper.toOrderDto(savedOrder);
    }
}
