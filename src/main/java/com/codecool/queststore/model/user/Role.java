package com.codecool.queststore.model.user;

public enum Role {

    MENTOR ("mentor"),
    STUDENT("student"),
    ADMIN("creepy guy");

    private final String NAME;
    Role(String name) {
        this.NAME = name;
    }
    public String getNAME() { return NAME; }
}
