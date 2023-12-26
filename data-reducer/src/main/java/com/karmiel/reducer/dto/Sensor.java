package com.karmiel.reducer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
@Data
@AllArgsConstructor
public class Sensor {
    @Id
    private String spotCoordinates;
    private Double quantity;
}
