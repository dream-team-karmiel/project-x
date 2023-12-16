package com.karmiel.reducer;

//import com.karmiel.reducer.Sensor;

import com.karmiel.reducer.repository.SensorRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@Import(TestChannelBinderConfiguration.class)
public class DataReducerAppTest {

    @Autowired(required = false)
    InputDestination producer;
    @Autowired(required = false)
    OutputDestination consumer;

    // Эмуляция репозитория
    @MockBean
    private SensorRepository repository;

    // Случай когда приходит значение в первые
    @Test
    public void ReceiveNewSensor() {
        // Создаение сенсора, который будет на входе
        Sensor newSensor = new Sensor("location1", 100.0);

        // Создаение when
        when(repository.findById("location1")).thenReturn(Optional.empty());
        when(repository.save(newSensor))
                .thenAnswer(invocation -> {
                    return invocation.<Sensor>getArgument(0);
                });
        // Отправка тестового сенсора
        producer.send(MessageBuilder.withPayload(newSensor).build());

        // Проверка, что на выходе есть сообщение
        Message<byte[]> receivedMessage = consumer.receive(1000, "Topic-1");
        assertNotNull("Message not received", receivedMessage);
        // Проверка, что на выходе есть сенсор с верными данными

        // Проверка, что был вызван метод findById и метод save у Redis
    }

    // Случай когда приходит значение без изменений
//    @Test
//    void ReceiveSensorWithSameData() {
    // Создание сенсора, который будет на входе

    // Создание when, что будет возвращаться сенсор со значением равным значению входящего сенсора

    // Отправка тестового сообщения

    // Проверка, что на выходе нет сообщения (равно null)
//    }

    // Случай когда приходит новое значение
//    @Test
//    void ReceiveSensorWithNewData() {
    // Создание тестового сенсора

    // Создание when, который будет возвращать сенсор с отличающимся значением

    // Отправка тестового сообщения

    // Проверка, что на выходе есть сообщение
    // Проверка, что на выходе есть сенсор с верными данными
    // Проверка, что был вызван метод findById и метод save у Redis
//    }
}


// Создание ответа Redis