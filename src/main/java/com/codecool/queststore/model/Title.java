package com.codecool.queststore.model;

public class Title {
    private final Integer ID;
    private final String NAME;

    public Title (Integer id, String name) {
        this.ID = id;
        this.NAME = name;
    }

    public int getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }
}
