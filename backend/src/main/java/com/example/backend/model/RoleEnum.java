package com.example.backend.model;

import lombok.Getter;

@Getter
public enum RoleEnum {
    CLIENT("CLIENT"), EMPLOYEE("EMPLOYEE"), ADMIN("ADMIN");

    String text;

    RoleEnum(String text) {
        this.text = text;
    }
}
