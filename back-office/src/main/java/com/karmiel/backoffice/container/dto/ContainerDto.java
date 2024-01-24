package com.karmiel.backoffice.container.dto;

import com.karmiel.backoffice.product.dto.ProductDto;


public record ContainerDto(
        String spotCoordinates,
        ProductDto product
        ) {}
