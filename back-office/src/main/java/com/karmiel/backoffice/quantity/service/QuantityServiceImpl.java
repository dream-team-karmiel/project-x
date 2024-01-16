package com.karmiel.backoffice.quantity.service;

import com.karmiel.backoffice.quantity.entity.Quantity;
import com.karmiel.backoffice.quantity.repository.QuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuantityServiceImpl implements QuantityService{
    @Autowired
    QuantityRepository repository;
    @Override
    public List<Quantity> getQuantitiesByContainerId(String containerId) {

        return repository.findByContainerId(containerId);
    }
}
