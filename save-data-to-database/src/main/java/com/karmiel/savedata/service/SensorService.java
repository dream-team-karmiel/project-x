package com.karmiel.savedata.service;

import com.karmiel.savedata.dto.Sensor;

public interface SensorService {

    void saveSensor(Sensor sensor);


    // 1. Получаем значение предыдущего сенсора из Redis
    // 2. Сравниваем значения
    // 3. Если значения отличаются, то меняем значение в Redis
    // 4. Отправляет сообщение Sensor с топиком "topic-0-1"

}
