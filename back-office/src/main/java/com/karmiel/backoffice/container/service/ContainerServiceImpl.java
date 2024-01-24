package com.karmiel.backoffice.container.service;

import com.karmiel.backoffice.container.dto.ContainerCreateDto;
import com.karmiel.backoffice.container.dto.ContainerDto;
import com.karmiel.backoffice.container.dto.ContainerMapper;
import com.karmiel.backoffice.container.dto.ContainerUpdateDto;
import com.karmiel.backoffice.container.entity.Container;
import com.karmiel.backoffice.container.repository.ContainerRepository;
import com.karmiel.backoffice.exception.ResourceNotFoundException;
import com.karmiel.backoffice.product.entity.Product;
import com.karmiel.backoffice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ContainerServiceImpl implements ContainerService{

    @Autowired
    ContainerRepository containerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ContainerMapper mapper;

    @Override
    public ContainerDto getContainerById(String id) {
        Container container = containerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Container by id: %s not found", id)));

        return mapper.toContainerDto(container);
    }

    @Override
    public List<ContainerDto> getAllContainers() {
        List<Container> allContainers = containerRepository.findAll();

        return mapper.toContainerDto(allContainers);
    }

    @Override
    public ContainerDto createContainer(ContainerCreateDto containerCreateDto) {
        Container container = new Container();
        container.setSpotCoordinates(containerCreateDto.spotCoordinates());
        return mapper.toContainerDto(containerRepository.save(container));
    }

    @Override
    public ContainerDto updateContainer(ContainerUpdateDto containerUpdateDto) {
        String id = containerUpdateDto.spotCoordinates();
        Container container = containerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Container by id: %s not found", id)));
        int productId = containerUpdateDto.productId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product by id: %d not found", productId)));
        container.setProduct(product);

        return mapper.toContainerDto(containerRepository.save(container));
    }
}
