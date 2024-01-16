package com.karmiel.backoffice.quantity.service;

import com.karmiel.backoffice.quantity.entity.Quantity;

import java.util.List;

public interface QuantityService {

    List<Quantity> getQuantitiesByContainerId (String containerId);
}
