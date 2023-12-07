package com.karmiel.savedata;

import com.karmiel.savedata.dto.Sensor;
import com.karmiel.savedata.repository.SensorRepository;
import com.karmiel.savedata.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

@SpringBootTest
@RequiredArgsConstructor
class SensorServiceImpTest {

    @MockBean
    SensorRepository sensorRepository;

    @MockBean
    private RedisTemplate<String, Sensor> redisTemplate;

    @Mock
    private ValueOperations<String, Sensor> valueOperations;
    @MockBean
    private KafkaTemplate<String, Sensor> kafkaTemplate;
    @Autowired
    private SensorService sensorService;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void saveSensorTest_NewSensor() {
        // Данные для теста
        Sensor newSensor = new Sensor("location1", 123.0);

        // Конфигурация моков
        when(redisTemplate.opsForValue().get(newSensor.spotCoordinates())).thenReturn(null);

        // Вызов метода
        sensorService.saveSensor(newSensor);

        // Проверка вызовов и ассертов
//        verify(redisTemplate, times(3)).opsForValue().set(eq(newSensor.spotCoordinates()), eq(newSensor));
        verify(kafkaTemplate).send(eq("topic-0-1"), eq(newSensor));
    }
// Тестирование Существующего Сенсора, Который Не Изменился
//    @Test
//    void saveSensorTest_ExistingUnchangedSensor() {
//        Sensor existingSensor = new Sensor("location1", 123.0);
//
//        // Конфигурация моков
//        when(redisTemplate.opsForValue().get(existingSensor.spotCoordinates())).thenReturn(existingSensor);
//
//        // Вызов метода
//        sensorService.saveSensor(existingSensor);
//
//        // Проверка, что методы для сохранения или отправки не вызывались
//        verify(redisTemplate, never()).opsForValue().set(any(), any());
//        verify(kafkaTemplate, never()).send(anyString(), any());
//    }
//
//    // Тестирование Существующего Сенсора с Измененными Данными
//    @Test
//    void saveSensorTest_ExistingChangedSensor() {
//        Sensor oldSensor = new Sensor("location1", 100.0);
//        Sensor newSensor = new Sensor("location1", 150.0);
//
//        // Конфигурация моков
//        when(redisTemplate.opsForValue().get(newSensor.spotCoordinates())).thenReturn(oldSensor);
//
//        // Вызов метода
//        sensorService.saveSensor(newSensor);
//
//        // Проверка вызовов и ассертов
//        verify(redisTemplate).opsForValue().set(eq(newSensor.spotCoordinates()), eq(newSensor));
//        verify(kafkaTemplate).send(eq("topic-0-1"), eq(newSensor));
//    }
//
//    // Тестирование Обработки Исключений
//    @Test
//    void saveSensorTest_ExceptionHandling() {
//        Sensor sensor = new Sensor("location1", 123.0);
//
//        // Конфигурация моков для имитации исключения
//        when(redisTemplate.opsForValue().get(anyString())).thenThrow(new RuntimeException("Redis error"));
//
//        // Вызов метода
//        assertThrows(RuntimeException.class, () -> sensorService.saveSensor(sensor));
//
//        // Проверка, что методы для сохранения или отправки не вызывались после исключения
//        verify(redisTemplate, never()).opsForValue().set(any(), any());
//        verify(kafkaTemplate, never()).send(anyString(), any());
//    }

}
