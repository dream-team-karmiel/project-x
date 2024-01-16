package com.karmiel.backoffice.container.repository;

import com.karmiel.backoffice.container.model.Measure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasureRepository extends JpaRepository<Measure, Integer> {
}
