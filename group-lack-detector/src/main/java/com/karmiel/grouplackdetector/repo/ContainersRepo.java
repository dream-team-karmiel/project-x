package com.karmiel.grouplackdetector.repo;

import com.karmiel.grouplackdetector.entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainersRepo extends JpaRepository<Container, String> {

}
