package com.karmiel.backoffice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.backoffice.container.controller.ContainerController;
import com.karmiel.backoffice.container.dto.ContainerDto;
import com.karmiel.backoffice.container.dto.MeasureDto;
import com.karmiel.backoffice.container.service.ContainerService;
import com.karmiel.backoffice.exception.ResourceNotFoundException;
import com.karmiel.backoffice.order.controller.OrderController;
import com.karmiel.backoffice.product.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;


import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {ContainerController.class})
public class ContainerControllerTest {
    private static final String EXIST_CONTAINER_ID = "A10";
    private static final String NOT_EXIST_CONTAINER_ID = "A12";
    private static final String CONTAINER_NOT_FOUND_MESSAGE = "Container by id: A12 not found";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    ContainerService containerService;
    MeasureDto measureDto = new MeasureDto(1, "liter");
    ProductDto productDto = new ProductDto(1, "Water",measureDto,100.0);
    ContainerDto containerDto = new ContainerDto("A10",productDto);

    @Test
    void getContainerByExistId() throws Exception {
        when(containerService.getContainerById(EXIST_CONTAINER_ID)).thenReturn(containerDto);
        String jsonContainer = mockMvc.perform(get("http://localhost:8080/containers/"+EXIST_CONTAINER_ID))
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        String expectedContainer = mapper.writeValueAsString(containerDto);
        assertEquals(jsonContainer, expectedContainer);

    }
    @Test
    void getContainerByNotExistId() throws Exception {
        when(containerService.getContainerById(NOT_EXIST_CONTAINER_ID))
                .thenThrow(new ResourceNotFoundException(CONTAINER_NOT_FOUND_MESSAGE));
        String responce = mockMvc.perform(get("http://localhost:8080/containers/"+NOT_EXIST_CONTAINER_ID))
                .andDo(print()).andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
        System.out.println(responce);
        assertEquals(CONTAINER_NOT_FOUND_MESSAGE, responce);
    }


}
