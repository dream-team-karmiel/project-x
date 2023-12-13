package com.karmiel.savedata.service;

import java.util.function.Consumer;

public interface SaveDataToDatabase
{
    public Consumer<String> receiveSensorData();
}
