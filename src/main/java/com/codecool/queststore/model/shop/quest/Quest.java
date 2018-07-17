package com.codecool.queststore.model.shop.quest;

public class Quest {
    private final String NAME;
    private final String DESCRIPTION;
    private final int value;
    private final boolean isDone;
    private final String imageName;
    private final String imageMarkedName;
    private QuestCategory category;


    public Quest(String name, String description, int value, String imageName, String imageMarkedName, QuestCategory category, boolean isDone) {
        NAME = name;
        DESCRIPTION = description;
        this.value = value;
        this.imageName = imageName;
        this.imageMarkedName = imageMarkedName;
        this.category = category;
        this.isDone = isDone;
    }

    public Quest(String name, String description, int value, String imageName, String imageMarkedName, boolean isDone) {
        this(name, description, value, imageName, imageMarkedName, QuestCategory.SIMPLE, isDone);
    }

}

