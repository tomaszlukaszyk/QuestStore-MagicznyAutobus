package com.codecool.queststore.dao.interfaces;

import com.codecool.queststore.model.shop.quest.Quest;
import com.codecool.queststore.model.shop.quest.QuestTemplate;

import java.util.List;

public interface QuestDAOInterface {

    QuestTemplate createTemplate(String name, String description, int value, boolean isGroup, String image, String imageMarked);
    QuestTemplate getQuestTemplate(int questID);
    void updateTemplate(int qtID, String name, String description, int value, String image, String imageMarked);

    List<QuestTemplate> getAllQuestTemplates();
    Quest getQuest(int historyID);
    List<Quest> getAllQuestsByUser(int userID);

    Quest createQuest(int templateID, int userID);
    Quest createQuest(QuestTemplate questTemplate, int userID);

    void markQuest(int historyID);
    void deleteQuest(int historyID);
    public boolean takeUpQuest(int userID, int questTemplateID, int value);

}
