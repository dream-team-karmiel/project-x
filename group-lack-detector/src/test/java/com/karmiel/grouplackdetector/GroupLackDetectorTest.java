package com.karmiel.grouplackdetector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.grouplackdetector.dto.ContainerData;
import com.karmiel.grouplackdetector.dto.FullData;
import com.karmiel.grouplackdetector.dto.OrderData;
import com.karmiel.grouplackdetector.entity.Container;
import com.karmiel.grouplackdetector.entity.Package;
import com.karmiel.grouplackdetector.entity.Product;
import com.karmiel.grouplackdetector.repo.ContainersRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;


import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class GroupLackDetectorTest {
    @Autowired
    InputDestination producer;
    @Autowired
    OutputDestination consumer;
    @MockBean
    ContainersRepo repo;
    String orderTopic = System.getenv("KAFKA_TOPIC_ORDER");
    String fullTopic = System.getenv("KAFKA_TOPIC_FULL");
    String containerTopic = System.getenv("KAFKA_TOPIC_CONTAINER");
    ObjectMapper mapper = new ObjectMapper();
    Package testPackage = new Package(1, "KG");
    Product testProduct = new Product
            (1, "TestProductName",testPackage, 123, 1000);
    Container testContainer = new Container(100, 1000.0, testProduct);
    ContainerData bigContainerData = new ContainerData("100", 70.0);
    ContainerData smallContainerData = new ContainerData("100", 40.0);
    FullData fullDataExpected = new FullData("100");
    OrderData orderDataExpected = new OrderData("100", "TestProductName", 60.0);

    @Test
    void lackDetectorTest() throws IOException {
        when(repo.findById(100)).thenReturn(Optional.ofNullable(testContainer));
       // System.out.println("Send topic: "+containerTopic+" Recieve full: "+fullTopic+" Recieve order: "+orderTopic);
        producer.send(new GenericMessage<ContainerData>(bigContainerData), containerTopic);
        Message<byte[]> messageBig = consumer.receive(100, fullTopic);
        assertNotNull(messageBig);
        assertEquals(fullDataExpected, mapper.readValue(messageBig.getPayload(), FullData.class));

        producer.send(new GenericMessage<ContainerData>(smallContainerData), containerTopic);
        Message<byte[]> messageSmall = consumer.receive(100, orderTopic);
        assertNotNull(messageSmall);
        assertEquals(orderDataExpected, mapper.readValue(messageSmall.getPayload(), OrderData.class));


    }

}
