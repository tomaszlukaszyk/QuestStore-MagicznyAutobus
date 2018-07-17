package com.codecool.queststore.model.shop.quest;

public class Quest {
    private final String NAME;
    private final String DESCRIPTION;
    private final int value;
    private final boolean IS_DONE;
    private final String IMAGE_FILENAME;
    private final String IMAGE_MARKED_FILENAME;
    private final QuestCategory CATEGORY;


    public Quest(String name, String description, int value, String imageName, String imageMarkedName, QuestCategory category, boolean isDone) {
        NAME = name;
        DESCRIPTION = description;
        this.value = value;
        this.IMAGE_FILENAME = imageName;
        this.IMAGE_MARKED_FILENAME = imageMarkedName;
        this.CATEGORY = category;
        this.IS_DONE = isDone;
    }

    public Quest(String name, String description, int value, String imageName, String imageMarkedName, boolean isDone) {
        this(name, description, value, imageName, imageMarkedName, QuestCategory.SIMPLE, isDone);
    }

}

