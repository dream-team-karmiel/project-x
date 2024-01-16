package com.karmiel.backoffice.container.service;

import com.karmiel.backoffice.container.dto.ContainerCreateDto;
import com.karmiel.backoffice.container.dto.ContainerDto;
import com.karmiel.backoffice.container.dto.ContainerUpdateDto;

import java.util.List;

public interface ContainerService {

    ContainerDto getContainerById (String id);
    List<ContainerDto> getAllContainers ();
    ContainerDto createContainer(ContainerCreateDto containerCreateDto);
    ContainerDto updateContainer(ContainerUpdateDto containerDto);


}
