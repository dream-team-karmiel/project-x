package com.karmiel.savedata.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;

import com.karmiel.savedata.dto.Sensor;
import com.karmiel.savedata.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorServiceImp implements SensorService {

    private final SensorRepository repository;
    private final RedisTemplate<String, Sensor> redisTemplate;
    private final KafkaTemplate<String, Sensor> kafkaTemplate;

    @Override
    public void saveSensor(Sensor sensor) {
//        Optional<Sensor> optionalSensor = repository.findById(sensor.spotCoordinates());
        Sensor previousSensor = redisTemplate.opsForValue().get(sensor.spotCoordinates());
        if (previousSensor == null || !previousSensor.equals(sensor)) {
            redisTemplate.opsForValue().set(sensor.spotCoordinates(), sensor);
            kafkaTemplate.send("topic-0-1", sensor);
        }
    }
}

// 1.1 Получаем значение предыдущего сенсора из Redis
// 1.2 Если значения нет, то мы добавляем в Redis и переходим к пункту 4
// 2. Сравниваем значения
// 3. Если значения отличаются, то меняем значение в Redis
// 4. Отправляет сообщение Sensor с топиком "topic-0-1"