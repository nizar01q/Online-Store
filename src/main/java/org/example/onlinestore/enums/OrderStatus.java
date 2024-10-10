package org.example.onlinestore.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus {
    PENDING("Pending"),
    PROCESSING("Processing"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled"),
    REQUEST_REFUND("Request refund"),
    REFUNDED("Refunded");

    private final String status;
}
