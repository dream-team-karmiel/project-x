package com.karmiel.backoffice.container.repository;

import com.karmiel.backoffice.container.entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContainerRepository extends JpaRepository<Container, String> {
}
