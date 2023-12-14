package com.karmiel.saverecordservice.service;

import java.util.function.Consumer;

public interface SaveRecordService {
    Consumer<String> saveRecordData();
}
