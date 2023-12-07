package com.karmiel.grouplackdetector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.grouplackdetector.dto.ContainerData;
import com.karmiel.grouplackdetector.dto.FullData;
import com.karmiel.grouplackdetector.dto.OrderData;
import com.karmiel.grouplackdetector.entity.Container;
import com.karmiel.grouplackdetector.entity.Package;
import com.karmiel.grouplackdetector.entity.Product;
import com.karmiel.grouplackdetector.service.GroupLackDetectorService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;


import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
//@Import(TestChannelBinderConfiguration.class)
public class GroupLackDetectorTest {
    @Resource
    InputDestination producer;
    @Resource
    OutputDestination consumer;
    @MockBean
    GroupLackDetectorService service;
    @Value("${spring.cloud.stream.bindings.order-out-0.destination}")
    String orderTopic;
    @Value("${spring.cloud.stream.bindings.full-out-0.destination}")
    String fullTopic;
    @Value("${spring.cloud.stream.bindings.recieveContainerData-in-0.destination}")
    String containerTopic;
    ObjectMapper mapper = new ObjectMapper();
    Package testPackage = new Package(1, "KG");
    Product testProduct = new Product
            (1, "Water", testPackage, 100);
    Container testContainer = new Container("A10", testProduct);
    ContainerData bigContainerData = new ContainerData("A10", 70.0);
    ContainerData smallContainerData = new ContainerData("A10", 40.0);
    FullData fullDataExpected = new FullData("A10");
    OrderData orderDataExpected = new OrderData("A10", "Water", 60.0);

    @Test
    void lackDetectorBigContainerTest() throws IOException {
        when(service.getContainer(bigContainerData)).thenReturn(testContainer);
        //System.out.println("Send topic: "+containerTopic+" Recieve full: "+fullTopic+" Recieve order: "+orderTopic);
        producer.send(new GenericMessage<>(bigContainerData), containerTopic);
        Message<byte[]> messageBig = consumer.receive(100, fullTopic);
        assertNotNull(messageBig);
        assertEquals(fullDataExpected, mapper.readValue(messageBig.getPayload(), FullData.class));
    }

    @Test
    void lackDetectorSmallContainerTest() throws IOException {
        when(service.getContainer(smallContainerData)).thenReturn(testContainer);
        System.out.println("Send topic: " + containerTopic + " Recieve full: " + fullTopic + " Recieve order: " + orderTopic);
        producer.send(new GenericMessage<>(smallContainerData), containerTopic);
        Message<byte[]> messageSmall = consumer.receive(100, orderTopic);
        assertNotNull(messageSmall);
        assertEquals(orderDataExpected, mapper.readValue(messageSmall.getPayload(), OrderData.class));
    }

}
