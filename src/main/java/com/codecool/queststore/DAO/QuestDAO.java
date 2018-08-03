package com.codecool.queststore.DAO;

import com.codecool.queststore.dao.interfaces.QuestDAOInterface;
import com.codecool.queststore.model.shop.quest.Quest;
import com.codecool.queststore.model.shop.quest.QuestCategory;
import com.codecool.queststore.model.shop.quest.QuestFactory;
import com.codecool.queststore.model.shop.quest.QuestTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestDAO implements Connectable, QuestDAOInterface {


    @Override
    public QuestTemplate createTemplate(String name, String description, int value, boolean isGroup, String image, String imageMarked) {
        try(Connection connection = cp.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT createquest(?, ?, ?, ?, ?, ?);")){

            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, value);
            statement.setBoolean(4, isGroup);
            statement.setString(5, image);
            statement.setString(6, imageMarked);

            QuestFactory questFactory = new QuestFactory();
            QuestCategory category = isGroup ? QuestCategory.SPECIAL : QuestCategory.NORMAL;
            statement.executeQuery();

            int templateID = getTemplateID(name, connection);

            return questFactory.templatefromData(templateID, name, description, value, image, imageMarked, category);
        } catch (SQLException e){
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public QuestTemplate getQuestTemplate(int questID) {
        try(Connection connection = cp.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM getquest(?)")){

            statement.setInt(1, questID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int value = resultSet.getInt("value");
                String imageName = resultSet.getString("first_image");
                String imageMarkedName = resultSet.getString("second_marked");
                int categoryValue = resultSet.getInt("questcategory");
                int templateID = getTemplateID(name, connection);

                QuestFactory questFactory = new QuestFactory();

                return questFactory.templatefromData(templateID, name, description, value, imageName, imageMarkedName, categoryValue);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateTemplate(int qtID, String name, String description, int value, String image, String imageMarked) {
        try(Connection connection = cp.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT updatequest(?, ?, ?, ?, ?, ?);")){

            statement.setInt(1, qtID);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setInt(4, value);
            statement.setString(5, image);
            statement.setString(6, imageMarked);

            statement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<QuestTemplate> getAllQuestTemplates() {
        try(Connection connection = cp.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM quest;")){

            List<QuestTemplate> templates = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            QuestFactory questFactory = new QuestFactory();

            while (resultSet.next()){
                String name = resultSet.getString("questname");
                String description = resultSet.getString("questdescription");
                int value = resultSet.getInt("questvalue");
                String imageName = resultSet.getString("image");
                String imageMarkedName = resultSet.getString("image_marked");
                int categoryValue = resultSet.getInt("idquestcategory");
                int templateID = getTemplateID(name, connection);
                templates.add(questFactory.templatefromData(templateID, name, description, value, imageName, imageMarkedName, categoryValue));
            }
                return templates;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Quest getQuest(int historyID) {
        try(Connection connection = cp.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM questhistory;")){

            ResultSet resultSet = statement.executeQuery();

            QuestFactory questFactory = new QuestFactory();

            if (resultSet.next()){
                QuestTemplate questTemplate = getQuestTemplate(resultSet.getInt("idquest"));
                String status = resultSet.getString("status");
                int studentID = resultSet.getInt("idstudent");

                return status.equals("DONE") ? questFactory.questfromTemplate(questTemplate, true, historyID, studentID) : questFactory.questfromTemplate(questTemplate, historyID, studentID);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Quest> getAllQuestsByUser(int userID) {
        try(Connection connection = cp.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM getstudentquests(?);")){

            int studentID = getStudentIDByUserID(userID, connection);

            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            QuestFactory questFactory = new QuestFactory();

            List<Quest> questList = new ArrayList<>();
            int lastTemplateID = 0;

            QuestTemplate questTemplate = null;

            while (resultSet.next()){
                int templateID = resultSet.getInt("questid");
                if (templateID == lastTemplateID){
                    // refactor needed as part is repeated
                    int historyID = resultSet.getInt("historyid");
                    String status = resultSet.getString("status_").toLowerCase();

                    Quest quest = status.equals("done") ? questFactory.questfromTemplate(questTemplate, true, historyID, studentID) : questFactory.questfromTemplate(questTemplate, historyID, studentID);
                    questList.add(quest);
                } else{
                    lastTemplateID = templateID;
                    questTemplate = getQuestTemplate(templateID);

                    int historyID = resultSet.getInt("historyid");
                    String status = resultSet.getString("status_").toLowerCase();

                    Quest quest = status.equals("done") ? questFactory.questfromTemplate(questTemplate, true, historyID, studentID) : questFactory.questfromTemplate(questTemplate, historyID, studentID);
                    questList.add(quest);
                }
            }
            return questList;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Quest createQuest(int templateID, int userID) {
        try(Connection connection = cp.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO questhistory(idstudent, idquest, value) VALUES (?, ?, ?);");
            PreparedStatement IDsearchStatement = connection.prepareStatement("SELECT idquesthistory FROM questhistory ORDER BY idquesthistory DESC LIMIT 1;")) {

            int studentID = getStudentIDByUserID(userID, connection);
            statement.setInt(1, studentID);
            statement.setInt(2, templateID);

            getQuestTemplate(templateID);
            QuestTemplate questTemplate = getQuestTemplate(templateID);
            int value = questTemplate.getValue();

            statement.setInt(3, value);
            statement.executeUpdate();

            ResultSet resultSet = IDsearchStatement.executeQuery();
            int historyID = 0;
            while(resultSet.next()){
                historyID = resultSet.getInt("idquesthistory");
            }

            QuestFactory questFactory = new QuestFactory();
            return questFactory.questfromTemplate(getQuestTemplate(templateID), historyID, studentID);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Quest createQuest(QuestTemplate questTemplate, int userID) {
        try(Connection connection = cp.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO questhistory(idstudent, idquest, value) VALUES (?, ?, ?);");
            PreparedStatement IDsearchStatement = connection.prepareStatement("SELECT idquesthistory FROM questhistory ORDER BY idquesthistory DESC LIMIT 1;")) {

            int studentID = getStudentIDByUserID(userID, connection);
            statement.setInt(1, studentID);
            statement.setInt(2, getTemplateID(questTemplate.getNAME(), connection));
            statement.setInt(3, questTemplate.getValue());
            statement.executeUpdate();

            ResultSet resultSet = IDsearchStatement.executeQuery();
            int historyID = 0;
            while(resultSet.next()){
                historyID = resultSet.getInt("idquesthistory");
            }

            QuestFactory questFactory = new QuestFactory();
            return questFactory.questfromTemplate(questTemplate, historyID, studentID);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void markQuest(int historyID) {
        try(Connection connection = cp.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE questhistory SET status='DONE' WHERE idquesthistory = ? AND status!='DONE';")) {

            statement.setInt(1, historyID);
            statement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteQuest(int historyID) {
        try(Connection connection = cp.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM questhistory WHERE idquesthistory = ? ;")) {

            statement.setInt(1, historyID);
            statement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean takeUpQuest(int userID, int questTemplateID, int value){
        try(Connection connection = cp.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO questhistory(idstudent, idquest, value, status) VALUES (?, ?, ?, 'NOT DONE');")) {

            int studentID = getStudentIDByUserID(userID, connection);

            statement.setInt(1, studentID);
            statement.setInt(2, questTemplateID);
            statement.setInt(3, value);
            statement.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    private int getStudentIDByUserID(int userID, Connection connection){
        try(PreparedStatement statement = connection.prepareStatement("SELECT idstudent FROM student WHERE iduser = ?")){

            statement.setInt(1, userID);
        ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt("idstudent");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    private int getTemplateID(String name, Connection connection){
        try(PreparedStatement statement = connection.prepareStatement("SELECT idquest FROM quest WHERE questname = ?")){
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt("idquest");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

}
