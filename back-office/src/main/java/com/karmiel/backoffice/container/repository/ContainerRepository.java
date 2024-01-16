package com.karmiel.backoffice.container.repository;

import com.karmiel.backoffice.container.model.Container;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContainerRepository extends PagingAndSortingRepository<Container, String> {
}
