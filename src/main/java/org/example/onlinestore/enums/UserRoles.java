package org.example.onlinestore.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRoles {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;
}
