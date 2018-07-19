package com.codecool.queststore.model.shop.quest;

import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.shop.artifact.ArtifactCategory;

import java.util.List;

public class QuestFactory {

    public List<Quest> listFromUserID(int id) {
        //todo: dao pullin all quests from userid
        return null;
    }
    public Quest fromData(int id, String name, String description, int cost, String imageName, String imageMarkedName, QuestCategory category, boolean isUsed) {
        return new Quest(id, name, description, cost, imageName, imageMarkedName, category, isUsed);
    }

    public Quest fromData(int id,String name, String description, int cost, String imageName, String imageMarkedName, QuestCategory category) {
        return new Quest(id, name, description, cost, imageName, imageMarkedName, category, false);
    }

}
