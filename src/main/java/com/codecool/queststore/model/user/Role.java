package com.codecool.queststore.model.user;

public enum Role {

    MENTOR ("mentor"),
    STUDENT("student"),
    ADMIN("admin");

    private final String NAME;
    Role(String name) {
        this.NAME = name;
    }
    public String getNAME() { return NAME; }
}
