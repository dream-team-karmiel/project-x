package com.karmiel.backoffice.order.controller;

import com.karmiel.backoffice.order.dto.OrderCreateRequestDto;
import com.karmiel.backoffice.order.dto.OrderDto;
import com.karmiel.backoffice.order.dto.OrderUpdateRequestDto;
import com.karmiel.backoffice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${com.karmiel.backoffice.endpoint.order}")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        log.trace("Getting all orders");
        return ResponseEntity.ok(service.getOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable String id) {
        log.trace("Getting order by id {}", id);
        return ResponseEntity.ok(service.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderCreateRequestDto requestDto, UriComponentsBuilder uriBuilder) {
        log.trace("Creating new order");
        OrderDto orderDto = service.createOrder(requestDto);
        URI uri = uriBuilder.path("${com.karmiel.backoffice.endpoint.order}/{id}").buildAndExpand(orderDto.id()).toUri();
        return ResponseEntity.created(uri).body(orderDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderUpdateRequestDto requestDto) {
        log.trace("Updating order");
        return ResponseEntity.ok(service.updateOrder(requestDto));
    }
}
