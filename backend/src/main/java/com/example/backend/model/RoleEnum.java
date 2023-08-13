package com.example.backend.model;

import lombok.Getter;

@Getter
public enum RoleEnum {
    BUYER("CLIENT"), SELLER("EMPLOYEE"), ADMIN("ADMIN");

    String text;

    RoleEnum(String text) {
        this.text = text;
    }
}
