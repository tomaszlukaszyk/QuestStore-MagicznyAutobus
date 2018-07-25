package com.codecool.queststore.model.shop.quest;

import com.codecool.queststore.model.shop.ShopItemAdapter;
import com.codecool.queststore.model.shop.artifact.Artifact;

import java.util.HashMap;
import java.util.Map;

public class QuestAdapter implements ShopItemAdapter {
    @Override
    public Map<String, String> getItemAsMap(Object item) {
        Map<String, String> questMap = new HashMap<>();

        try {
            Quest quest = (Quest) item;

            questMap.put("id", String.valueOf(quest.getQUEST_HISTORY_ID()));
            questMap.put("title", quest.getNAME());
            questMap.put("description", quest.getDESCRIPTION());
            questMap.put("category", quest.getCATEGORY().getCATEGORY());
            questMap.put("value", String.valueOf(quest.getValue()));
            questMap.put("imageName", quest.getIMAGE_FILENAME());
            questMap.put("markedImageName", quest.getIMAGE_MARKED_FILENAME());

            return questMap;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }
}
