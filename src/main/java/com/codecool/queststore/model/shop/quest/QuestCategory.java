package com.codecool.queststore.model.shop.quest;

public enum QuestCategory {

    SIMPLE ("basic"),
    SPECIAL("extra");

    private final String CATEGORY;
    QuestCategory(String name) {
        this.CATEGORY = name;
    }
    public String getCATEGORY() {
        return CATEGORY; }

}
