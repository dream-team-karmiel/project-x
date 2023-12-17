package com.karmiel.saverecordservice.dto;

public record NewOrder(String spotCoordinates, String productName, Double requiredQuantity) {
}