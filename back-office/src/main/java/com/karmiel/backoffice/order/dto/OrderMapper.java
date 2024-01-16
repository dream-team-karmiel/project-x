package com.karmiel.backoffice.order.dto;

import com.karmiel.backoffice.order.model.Order;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @BeanMapping(ignoreByDefault = true)
    Order toOrder(OrderCreateRequestDto requestDto);

    OrderDto toOrderDto(Order order);

    List<OrderDto> toOrderDto(List<Order> orders);
}
