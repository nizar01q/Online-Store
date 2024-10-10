package org.example.onlinestore.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserStatus {
    ACTIVE("Active"),
    SUSPEND("Suspended");

    private final String status;
}

