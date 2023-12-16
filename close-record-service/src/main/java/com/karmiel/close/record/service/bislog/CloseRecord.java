package com.karmiel.close.record.service.bislog;

import com.karmiel.close.record.service.dto.CloseRecordDto;
import com.karmiel.close.record.service.entities.Order;

public interface CloseRecord {
    void closeRecord (CloseRecordDto closeRecordDto) ;
}
