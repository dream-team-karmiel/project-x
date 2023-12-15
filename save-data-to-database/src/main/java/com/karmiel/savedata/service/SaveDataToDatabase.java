package com.karmiel.savedata.service;

import com.karmiel.savedata.dto.Sensor;

import java.util.function.Consumer;

public interface SaveDataToDatabase
{
    public Consumer<String> receiveSensorData();
}
