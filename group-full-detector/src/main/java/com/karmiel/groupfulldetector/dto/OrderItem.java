package com.karmiel.groupfulldetector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Document(collection = "orders data")
public class OrderItem implements Serializable {
    @Id
    public long id;
    public String spotCoordinates;
    public LocalDate creationDate;
    public LocalDate closeDate;
    public String orderStatus;

}
