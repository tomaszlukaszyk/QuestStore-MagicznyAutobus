package com.codecool.queststore.model.shop.quest;

import java.util.HashMap;
import java.util.Map;

public enum QuestCategory {

    PERSONAL(1),
    GROUP(2);

    private final int VALUE;
    private static final Map<Integer, QuestCategory> categoryMap = new HashMap<>();
    static {
        for (QuestCategory questCategory : values()) {
            categoryMap.put(questCategory.getVALUE(), questCategory);
        }
    }
    QuestCategory(int name) {
        this.VALUE = name;
    }

    public static QuestCategory getByValue(int category){
        return categoryMap.get(category);
    }

    public int getVALUE() {
        return VALUE; }

}
