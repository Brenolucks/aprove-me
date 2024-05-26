package com.brenolucks.aproveMe.domain.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    USER("user");
    private String role;
    UserRole(String role){
        this.role = role;
    }

    public static UserRole conversionRole(int role){
        var roleConverted = switch (role) {
            case 1 -> ADMIN;
            case 0 -> USER;
            default -> throw new IllegalArgumentException("Role digitada incorretamente");
        };

        return roleConverted;
    }
}
