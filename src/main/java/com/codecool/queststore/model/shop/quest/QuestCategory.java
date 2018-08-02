package com.codecool.queststore.model.shop.quest;

import java.util.HashMap;
import java.util.Map;

public enum QuestCategory {

    NORMAL(1, "normal"),
    SPECIAL(2, "special");

    private final int VALUE;
    private final String asSTRING;

    private static final Map<Integer, QuestCategory> categoryValueMap = new HashMap<>();
    private static final Map<String, QuestCategory> categoryStringMap = new HashMap<>();
    static {
        for (QuestCategory questValueCategory : values()) {
            categoryValueMap.put(questValueCategory.getVALUE(), questValueCategory);
        }
        for (QuestCategory questStringCategory : values()) {
            categoryStringMap.put(questStringCategory.getAsSTRING(), questStringCategory);
        }
    }



    QuestCategory(int name, String asSTRING) {
        this.VALUE = name;
        this.asSTRING = asSTRING;
    }

    public static QuestCategory getByValue(int category){
        return categoryValueMap.get(category);
    }

    public static QuestCategory getByString(String asString){
        return categoryStringMap.get(asString);
    }

    public int getVALUE() {
        return VALUE;
    }

    public String getAsSTRING() {
        return asSTRING;
    }
}
