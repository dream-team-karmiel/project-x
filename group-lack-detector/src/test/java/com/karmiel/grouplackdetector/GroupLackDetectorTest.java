package com.karmiel.grouplackdetector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmiel.grouplackdetector.dto.ContainerData;
import com.karmiel.grouplackdetector.dto.FullData;
import com.karmiel.grouplackdetector.dto.OrderData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class GroupLackDetectorTest {
    @Autowired
    InputDestination producer;
    @Autowired
    OutputDestination consumer;
    String orderTopic = System.getenv("KAFKA_TOPIC_ORDER");
    String fullTopic = System.getenv("KAFKA_TOPIC_FULL");
    String containerTopic = System.getenv("KAFKA_TOPIC_CONTAINER");
    ObjectMapper mapper = new ObjectMapper();
    ContainerData bigContainerData = new ContainerData("001", 70.0);
    ContainerData smallContainerData = new ContainerData("001", 40.0);
    FullData fullDataExpected = new FullData("001");
    OrderData orderDataExpected = new OrderData("001", "TestProductName", 60.0);

    @Test
    void lackDetectorTest() throws IOException {

        System.out.println("Send topic: "+containerTopic+" Recieve full: "+fullTopic+" Recieve order: "+orderTopic);
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
