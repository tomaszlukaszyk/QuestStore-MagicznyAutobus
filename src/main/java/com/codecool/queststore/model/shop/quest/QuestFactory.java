package com.codecool.queststore.model.shop.quest;

import com.codecool.queststore.DAO.QuestDAO;
import com.codecool.queststore.dao.interfaces.QuestDAOInterface;

import java.util.List;

public class QuestFactory {

    public List<Quest> listFromUserID(int id) {
        QuestDAOInterface dao = new QuestDAO();
        return dao.getAllQuestsByUser(id);
    }

    public List<QuestTemplate> getTemplates() {
        QuestDAOInterface dao = new QuestDAO();
        return dao.getAllQuestTemplates();
    }


    public QuestTemplate templatefromData(int ID, String name, String description, int value, String imageName, String imageMarkedName, QuestCategory category){
        return new QuestTemplate(ID, name, description, value, imageName, imageMarkedName, category);
    }

    public QuestTemplate templatefromData(int ID, String name, String description, int value, String imageName, String imageMarkedName, int categoryVal){
        return new QuestTemplate(ID, name, description, value, imageName, imageMarkedName, categoryVal);
    }

    public Quest questfromTemplate(QuestTemplate questTemplate, boolean isDone, int historyID, int studentID){
        return new Quest(questTemplate, isDone, historyID, studentID);
    }

    public Quest questfromTemplate(QuestTemplate questTemplate, int historyID, int studentID){
        return new Quest(questTemplate, false, historyID, studentID);
    }

}
