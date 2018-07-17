package com.codecool.queststore.model.shop.quest;

public enum QuestCategory {

    SIMPLE ("simple"),
    SPECIAL("special");

    private final String CATEGORY;
    QuestCategory(String name) {
        this.CATEGORY = name;
    }
    public String getCATEGORY() {
        return CATEGORY; }

}
