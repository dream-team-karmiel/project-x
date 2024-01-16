package com.karmiel.backoffice.container.dto;

import com.karmiel.backoffice.product.entity.Product;

public record ContainerDto(
        String spotCoordinates,
        Product product
        ) {}
