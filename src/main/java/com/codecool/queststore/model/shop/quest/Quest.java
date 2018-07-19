package com.codecool.queststore.model.shop.quest;

public class Quest {
    private final String NAME;
    private final String DESCRIPTION;
    private final int value;
    private final boolean IS_DONE;
    private final String IMAGE_FILENAME;
    private final String IMAGE_MARKED_FILENAME;
    private final QuestCategory CATEGORY;
    private final int QUEST_HISTORY_ID;


    public Quest(int id, String name, String description, int value, String imageName, String imageMarkedName, QuestCategory category, boolean isDone) {
        this.NAME = name;
        this.DESCRIPTION = description;
        this.value = value;
        this.IMAGE_FILENAME = imageName;
        this.IMAGE_MARKED_FILENAME = imageMarkedName;
        this.CATEGORY = category;
        this.IS_DONE = isDone;
        this.QUEST_HISTORY_ID = id;
    }

    public Quest(int id, String name, String description, int value, String imageName, String imageMarkedName, boolean isDone) {
        this(id, name, description, value, imageName, imageMarkedName, QuestCategory.SIMPLE, isDone);
    }

}

