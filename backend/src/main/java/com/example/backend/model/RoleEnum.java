package com.example.backend.model;

import lombok.Getter;

/**
 * Enum role
 */
@Getter
public enum RoleEnum {
    CLIENT("CLIENT"), EMPLOYEE("EMPLOYEE"), ADMIN("ADMIN");

    String text;

    /**
     * Constructor for role enum
     * @param text role
     */
    RoleEnum(String text) {
        this.text = text;
    }
}
