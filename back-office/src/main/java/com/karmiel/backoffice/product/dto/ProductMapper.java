package com.karmiel.backoffice.product.dto;

import com.karmiel.backoffice.product.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct (ProductDto productDto);
    ProductDto toProductDto (Product product);
}
