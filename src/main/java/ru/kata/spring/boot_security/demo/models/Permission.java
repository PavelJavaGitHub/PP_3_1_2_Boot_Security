package ru.kata.spring.boot_security.demo.models;

import org.springframework.beans.factory.annotation.Autowired;

public enum Permission {
    READ("read"),
    ADMINISTRATE("administrate");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
