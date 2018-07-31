package com.codecool.queststore.dao.interfaces;

import com.codecool.queststore.model.shop.quest.Quest;
import com.codecool.queststore.model.shop.quest.QuestTemplate;

import java.util.List;

public interface QuestDAOInterface {

    QuestTemplate createTemplate(String name, String description, int value, boolean isGroup, String image, String imageMarked);
    QuestTemplate getQuestTemplate(int questID);
    void updateTemplate(int questID, String name, String description, int value, int categoryValue, String image, String imageMarked);

    Quest getQuest(int historyID);
    List<Quest> getAllQuestsByUser(int userID);

    Quest createQuest(int templateID, int userID);
    Quest createQuest(QuestTemplate questTemplate, int userID);

    void markQuest(int historyID);
    void deleteQuest(int historyID);

}
