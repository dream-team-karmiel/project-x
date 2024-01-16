package com.karmiel.backoffice.quantity.controller;

import com.karmiel.backoffice.quantity.entity.Quantity;
import com.karmiel.backoffice.quantity.repository.QuantityRepository;
import com.karmiel.backoffice.quantity.service.QuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quantities")
public class QuantityController {

    @Autowired
    QuantityService service;
    @GetMapping("/container/{id}")
    public ResponseEntity<List<Quantity>> getQuantitiesByContainerId(@PathVariable("id") String containerId) {

        return ResponseEntity.ok(service.getQuantitiesByContainerId(containerId));
    }
}
