package com.karmiel.grouplackdetector.repository;

import com.karmiel.grouplackdetector.dto.ContainerData;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InMemoryRepositoryImpl implements ContainerRepository {
    @Override
    public Optional<ContainerData> findDataById(String spotCoordinates) {
        return Optional.of(new ContainerData(spotCoordinates, "Water", 100.));
    }
}
