package com.karmiel.backoffice.container.dto;

import com.karmiel.backoffice.container.entity.Container;
import com.karmiel.backoffice.product.dto.ProductDto;
import com.karmiel.backoffice.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContainerMapper {
    ContainerDto toContainerDto (Container container);
    List<ContainerDto> toContainerDto(List<Container> containers);

    Container toContainer (ContainerDto containerDto);

    Product toProduct (ProductDto productDto);
    ProductDto toProductDto (Product product);


}
