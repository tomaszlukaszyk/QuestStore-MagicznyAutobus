package com.codecool.queststore.model.shop.artifact;

public enum ArtifactCategory {

    PERSONAL ("personal"),
    GROUP("group");

    private final String CATEGORY;
    ArtifactCategory(String name) {
        this.CATEGORY = name;
    }
    public String getCATEGORY() {
        return CATEGORY; }
}

