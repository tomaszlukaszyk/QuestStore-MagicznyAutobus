package com.codecool.queststore.model.shop.quest;

import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.shop.artifact.ArtifactCategory;

import java.util.List;

public class QuestFactory {

    public List<Quest> listFromUserID(int id) {



        return null;
    }

    public QuestTemplate templatefromData(String name, String description, int value, String imageName, String imageMarkedName, QuestCategory category){
        return new QuestTemplate(name, description, value, imageName, imageMarkedName, category);
    }

    public QuestTemplate templatefromData(String name, String description, int value, String imageName, String imageMarkedName, int categoryVal){
        return new QuestTemplate(name, description, value, imageName, imageMarkedName, categoryVal);
    }

    public Quest questfromTemplate(QuestTemplate questTemplate, boolean isDone, int historyID, int studentID){
        return new Quest(questTemplate, isDone, historyID, studentID);
    }

    public Quest questfromTemplate(QuestTemplate questTemplate, int historyID, int studentID){
        return new Quest(questTemplate, false, historyID, studentID);
    }

}
