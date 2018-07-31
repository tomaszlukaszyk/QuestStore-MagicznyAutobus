package com.codecool.queststore.model.shop.quest;

public class QuestTemplate {
    private final String NAME;
    private final String DESCRIPTION;
    private final int value;
    private final String IMAGE_FILENAME;
    private final String IMAGE_MARKED_FILENAME;
    private final QuestCategory CATEGORY;


    public QuestTemplate(String name, String description, int value, String imageName, String imageMarkedName, int category) {
        this.NAME = name;
        this.DESCRIPTION = description;
        this.value = value;
        this.IMAGE_FILENAME = imageName;
        this.IMAGE_MARKED_FILENAME = imageMarkedName;
        this.CATEGORY = QuestCategory.getByValue(category);
    }

    public QuestTemplate(String name, String description, int value, String imageName, String imageMarkedName, QuestCategory category) {
        this.NAME = name;
        this.DESCRIPTION = description;
        this.value = value;
        this.IMAGE_FILENAME = imageName;
        this.IMAGE_MARKED_FILENAME = imageMarkedName;
        this.CATEGORY = category;
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

    public String getIMAGE_FILENAME() {
        return IMAGE_FILENAME;
    }

    public String getIMAGE_MARKED_FILENAME() {
        return IMAGE_MARKED_FILENAME;
    }

    public QuestCategory getCATEGORY() {
        return CATEGORY;
    }



}


