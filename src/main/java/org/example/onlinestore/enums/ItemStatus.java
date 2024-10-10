package org.example.onlinestore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemStatus {
    OUT_OF_STOCK("Out of stock"),
    IN_STOCK("In stock");

    private final String status;
}
