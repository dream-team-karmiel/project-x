package com.karmiel.grouplackdetector.repository;

import com.karmiel.grouplackdetector.model.Container;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerRepository extends CrudRepository<Container, String> {
}
