package com.karmiel.savedata.service;

import com.karmiel.savedata.dto.QuantityDto;
import com.karmiel.savedata.dto.QuantityMapper;
import com.karmiel.savedata.model.Quantity;
import com.karmiel.savedata.repository.QuantityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SavaDataServiceImpl implements SavaDataService {
    private final QuantityMapper mapper;
    private final QuantityRepository repository;

    @Override
    public void saveData(QuantityDto data) {
        Quantity quantity = mapper.toQuantity(data);
        Quantity save = repository.save(quantity);
        log.trace("Saved to DB: {}", save);
    }
}
