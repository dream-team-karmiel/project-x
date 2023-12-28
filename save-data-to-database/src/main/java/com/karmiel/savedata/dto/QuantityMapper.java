package com.karmiel.savedata.dto;

import com.karmiel.savedata.model.Quantity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuantityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "containerId", source = "spotCoordinates")
    @Mapping(target = "sensorDate", expression = "java(java.time.LocalDateTime.now())")
    Quantity toQuantity(QuantityDto quantityDto);

}
