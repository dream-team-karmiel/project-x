package com.karmiel.close.record.service.bislog;

import com.karmiel.close.record.service.dto.CloseRecordDto;
import com.karmiel.close.record.service.entities.Order;
import com.karmiel.close.record.service.enums.OrderStatus;
import com.karmiel.close.record.service.repo.OrdersDataRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloseRecordImpl implements CloseRecord{

    final OrdersDataRepo ordersDataRepo;
    @Override
    public void closeRecord(CloseRecordDto closeRecordDto) {

        String id = closeRecordDto.getId();
        Order order = ordersDataRepo.findById(id).orElse(null);

        if(order == null){
            log.debug("order with id {} is not found ", id);
            return;
        }

        order.setCloseDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.DONE);
        try {
            ordersDataRepo.save(order);
            log.trace("Order {} update", id);
        } catch (Exception e) {
            log.trace("Order isn`t saved");
            throw new RuntimeException(e);
        }

    }
}
