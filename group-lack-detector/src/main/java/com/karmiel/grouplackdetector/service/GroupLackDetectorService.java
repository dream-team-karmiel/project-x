package com.karmiel.grouplackdetector.service;

import com.karmiel.grouplackdetector.dto.ContainerData;
import com.karmiel.grouplackdetector.entity.Container;
import com.karmiel.grouplackdetector.repo.ContainersRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class GroupLackDetectorService {
    @Autowired
    ContainersRepo containersRepo;

    public Container getContainer(ContainerData data) {
        log.trace("Get container from repo with container id: {}", data.spotCoordinates());
        String containerId = data.spotCoordinates();
        return containersRepo.findById(containerId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Container with id %s not exist", containerId)));
    }
}
