package com.karmiel.grouplackdetector.repository;

import com.karmiel.grouplackdetector.dto.ContainerData;

import java.util.Optional;

public interface ContainerRepository {
    Optional<ContainerData> findDataById(String spotCoordinates);
}
