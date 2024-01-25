package com.karmiel.backoffice.container.controller;

import com.karmiel.backoffice.container.dto.ContainerCreateDto;
import com.karmiel.backoffice.container.dto.ContainerDto;
import com.karmiel.backoffice.container.dto.ContainerUpdateDto;
import com.karmiel.backoffice.container.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/containers")
public class ContainerController {

    @Autowired
    ContainerService service;


    @GetMapping("/{id}")
    public ResponseEntity<ContainerDto> getContainerById(@PathVariable String id) {

        return ResponseEntity.ok(service.getContainerById(id));
    }

    @GetMapping
    public ResponseEntity<List<ContainerDto>> getAllContainers() {
        return ResponseEntity.ok(service.getAllContainers());
    }

    @PostMapping
    public ResponseEntity<ContainerDto> createContainer(@RequestBody ContainerCreateDto containerCreateDto) {

        return ResponseEntity.status(201).body(service.createContainer(containerCreateDto));
    }


    @PatchMapping
    public ResponseEntity<ContainerDto> updateContainer(@RequestBody ContainerUpdateDto containerUpdateDto) {


        return ResponseEntity.status(200).body(service.updateContainer(containerUpdateDto));
    }
}
