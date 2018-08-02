package com.codecool.queststore.controller.server.service;

import com.codecool.queststore.DAO.QuestDAO;
import com.codecool.queststore.controller.server.httphandler.AbstractHttphandler;
import com.codecool.queststore.dao.interfaces.QuestDAOInterface;
import com.codecool.queststore.model.server.session.SessionPool;
import com.codecool.queststore.model.shop.quest.Quest;
import com.codecool.queststore.model.shop.quest.QuestFactory;
import com.codecool.queststore.model.shop.quest.QuestTemplate;
import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;
import com.codecool.queststore.view.RenderInteface;
import com.codecool.queststore.view.TemplateRender;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class QuestService extends AbstractHttphandler {

    private QuestFactory questFactory = new QuestFactory();
    private QuestDAOInterface questDAO = new QuestDAO();
    private TemplateRender templateRender = new TemplateRender();


    public void handleSession(HttpExchange httpExchange) throws IOException, SQLException {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = new HttpCookie("Session-id", cookieStr);
        User user = new com.codecool.queststore.DAO.UserDAO().getUser(SessionPool.getSessionByUUID(UUID.fromString(cookie.getValue())).getUSER_ID());
        String viewStudentQuestPath = "/quests/view";
        String markQuestPath = "/quests/mark";
        String requestURIString = httpExchange.getRequestURI().toString();


        if ((Role.MENTOR.equals(user.getROLE()) || Role.STUDENT.equals(user.getROLE())) && requestURIString.contains(viewStudentQuestPath)){
            viewStudentQuests(user, httpExchange);
        } else if(Role.MENTOR.equals(user.getROLE()) && requestURIString.contains(markQuestPath)){
            markStudentQuest(httpExchange);
        }

        if(Role.MENTOR.equals(user.getROLE())){

            String editTemplatePath = "/quests/edit";
            String addTemplatePath = "/quests/add";
            if (requestURIString.equals(addTemplatePath)){
                handleAddTemplate(user, httpExchange);
            } else if(requestURIString.contains(editTemplatePath)) {
                editTemplate(user, httpExchange);
            } else{
                // generate mentor questTemplate view
                List<QuestTemplate> questTemplates = questFactory.getTemplates();
                String response = templateRender.RenderQuestTemplatesPage(user, questTemplates);
                SendReq(httpExchange, response);
            }

        }else if(Role.STUDENT.equals(user.getROLE())){
            // generate student view
            handleStudentView(user, httpExchange);
        }

    }

    private void handleAddTemplate(User currentUser, HttpExchange httpExchange) throws IOException{
        if(httpExchange.getRequestMethod().equals("GET")){
            addTemplateGet(currentUser, httpExchange);
        } else {
            addTemplatePost(httpExchange);
        }
    }

    private void addTemplateGet(User currentUser, HttpExchange httpExchange) throws IOException{
        RenderInteface templateRender = new TemplateRender();
        String response = templateRender.rendeAddQuestTemplatesPage(currentUser);
        SendReq(httpExchange, response);
    }

    private QuestTemplate addTemplatePost(HttpExchange httpExchange) throws IOException{
        Map<String, String> parsedData = parseFormData(httpExchange);
        String questName = parsedData.get("name");
        String questDescription = parsedData.get("description");
        int value = Integer.parseInt(parsedData.get("value"));
        String file1 = parsedData.get("image1");
        String file2 = parsedData.get("image2");
        boolean isGroup = parsedData.get("category").equals("group");

        QuestTemplate createdTemplate = questDAO.createTemplate(questName, questDescription, value, isGroup, file1, file2);
        redirect(httpExchange, "/quests");
        return createdTemplate;
    }

    private void editTemplate (User currentUser, HttpExchange httpExchange) throws IOException{
        if (httpExchange.getRequestMethod().equals("GET")){
            editHandleGet(currentUser, httpExchange);
        } else {
            editHandlePost(httpExchange);
        }
    }

    private void editHandlePost(HttpExchange httpExchange) throws IOException{
        int templateID = Integer.parseInt(URI.create("/quests/edit/").relativize(httpExchange.getRequestURI()).toString());
        Map<String, String> parsedData = parseFormData(httpExchange);
        String questName = parsedData.get("name");
        String questDescription = parsedData.get("description");
        int value = Integer.parseInt(parsedData.get("value"));
        String file1 = parsedData.get("image1");
        String file2 = parsedData.get("image2");
        questDAO.updateTemplate(templateID, questName, questDescription, value, file1, file2);
        redirect(httpExchange, "/quests");
    }

    private void editHandleGet(User currentUser, HttpExchange httpExchange) throws IOException{
        int templateID = Integer.parseInt(URI.create("/quests/edit/").relativize(httpExchange.getRequestURI()).toString());
        QuestTemplate questTemplate = questDAO.getQuestTemplate(templateID);

        if(questTemplate != null) {
            List<QuestTemplate> questTemplates = new ArrayList<>();
            questTemplates.add(questTemplate);
            RenderInteface templateRender = new TemplateRender();
            String response = templateRender.RenderEditQuestTemplatesPage(currentUser, questTemplates);
            SendReq(httpExchange, response);
        }
        else {
            redirect(httpExchange, "/quests");
        }
    }

    private void handleStudentView(User user, HttpExchange httpExchange) throws IOException{

        if (httpExchange.getRequestURI().toString().contains("quests/take/")){
            int templateID = Integer.parseInt(URI.create("/quests/take/").relativize(httpExchange.getRequestURI()).toString());
            QuestTemplate questTemplate = questDAO.getQuestTemplate(templateID);
            questDAO.takeUpQuest(user.getID(), templateID,questTemplate.getValue());
            redirect(httpExchange, "/quests/");
        } else {
            List<QuestTemplate> questTemplates = questFactory.getTemplates();
            String response = templateRender.RenderQuestTemplatesPage(user, questTemplates);
            SendReq(httpExchange, response);
        }
    }

    private void viewStudentQuests(User user, HttpExchange httpExchange) throws IOException{
        if (user.getROLE().equals(Role.MENTOR)){
            int userID = Integer.parseInt(URI.create("/quests/view/").relativize(httpExchange.getRequestURI()).toString());
            List<Quest> temp = questFactory.listFromUserID(userID);
            List<Quest> quests = new ArrayList<>();
            for(Quest quest : temp) {
                if (!quest.IS_DONE()) {
                    quests.add(quest);
                }
            }

            String response = templateRender.RenderQuestPage(user, quests);
            SendReq(httpExchange, response);
        } else {
            List<Quest> quests = questFactory.listFromUserID(user.getID());

            String response = templateRender.RenderQuestPage(user, quests);
            SendReq(httpExchange, response);
        }
    }

    private void markStudentQuest(HttpExchange httpExchange) throws IOException{
        int questID = Integer.parseInt(URI.create("/quests/mark/").relativize(httpExchange.getRequestURI()).toString());
        System.out.println(URI.create("/quests/mark/").relativize(httpExchange.getRequestURI()).toString());
        System.out.println("questID: " + questID);
        questDAO.markQuest(questID);
        redirect(httpExchange, "/quests");
    }
}
