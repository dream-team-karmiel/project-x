package com.karmiel.backoffice.container.dto;

import com.karmiel.backoffice.container.entity.Container;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContainerMapper {
    ContainerDto toContainerDto (Container container);
    List<ContainerDto> toContainerDto(List<Container> containers);

    Container toContainer (ContainerDto containerDto);


}
