package com.codecool.queststore.controller.server.service;

import com.codecool.queststore.DAO.ArtifactDAO;
import com.codecool.queststore.DAO.TitleDAO;
import com.codecool.queststore.DAO.UserDAO;
import com.codecool.queststore.model.Title;
import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.server.session.SessionPool;
import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;
import com.codecool.queststore.view.TemplateRender;

import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class ProfileService {
    private final HttpCookie cookie;
    private final String path;

    public ProfileService(HttpCookie cookie, String path) {
        this.cookie = cookie;
        this.path = path;
    }
    public void addNewTitle(String name) {
        try {
            new TitleDAO().createTitle(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editTitle(Integer id, String name) {
        try {
            new TitleDAO().updateTitle(new Title(id, name));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handlePost(Map inputs) throws SQLException {
        String submit = (String)inputs.get("submit");

        if (submit.equals("new")) {
            addNewTitle((String)inputs.get("name"));
        } else {
            editTitle(Integer.parseInt(submit),(String)inputs.get("name"));
        }



    }

    public String generateResponseBody() throws SQLException {
        User currentUser = new UserDAO()
                .getUser(SessionPool
                        .getSessionByUUID(UUID
                                .fromString(cookie.getValue()))
                        .getUSER_ID());

        User targetUser = defineTarget(currentUser, path);

        switch(targetUser.getROLE()) {

            case STUDENT:
                //todo: fill class  by theirs daos
                System.out.println(targetUser.getADDRESS());
                return new TemplateRender().RenderProfilePage
                        (currentUser, targetUser, new CodecoolClass("LOL"),
                                new ArtifactDAO().getUsersNotUsedArtifactsById(targetUser.getID()));

            case MENTOR:
                //todo: fill classes by their daos
                System.out.println(targetUser.getADDRESS());
                return new TemplateRender().RenderProfilePage
                        (currentUser, targetUser, new ArrayList<>());

            case ADMIN:
                if (currentUser.getROLE() == Role.ADMIN)
                   return new TemplateRender().RenderProfilePage(currentUser, new TitleDAO().getTitlesList());
                else
                    return new TemplateRender().RenderProfilePage
                            (currentUser, currentUser, new CodecoolClass("LOL"),
                                    new ArtifactDAO().getUsersNotUsedArtifactsById(targetUser.getID()));
        }
        return null;

    }

    private User defineTarget(User currentUser, String path) throws SQLException {
        Integer userID = getTargetUserID(splitURL(path));
        User target = null;

        if (userID != null)
            target = new UserDAO().getUser(userID);

        if (target != null) {
            return target;
        }

            return currentUser;

    }

    private String[] splitURL(String path) {
        return path.split("/");
    }

    private Integer getTargetUserID(String[] array) {
        final int USER_ID_PLACE = 2;

        if(array.length > USER_ID_PLACE) {
            if(isStringCastableToInt(array[USER_ID_PLACE])) {
                return Integer.parseInt(array[USER_ID_PLACE]);
            }
        }
        return null;
    }

    private boolean isStringCastableToInt(String string) {
        try{
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
