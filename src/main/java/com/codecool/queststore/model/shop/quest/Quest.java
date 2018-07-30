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

    public String getNAME() {
        return NAME;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public int getValue() {
        return value;
    }

    public boolean IS_DONE() {
        return IS_DONE;
    }

    public String getIMAGE_FILENAME() {
        return IMAGE_FILENAME;
    }

    public String getIMAGE_MARKED_FILENAME() {
        return IMAGE_MARKED_FILENAME;
    }

    public String getCATEGORY() {
        return CATEGORY.getCATEGORY();
    }

    public int getQUEST_HISTORY_ID() {
        return QUEST_HISTORY_ID;
    }
}

