package com.karmiel.backoffice.container.dto;

import com.karmiel.backoffice.container.model.Measure;

public record ContainerDto(
        String spotCoordinates,
        Measure measure) {}
