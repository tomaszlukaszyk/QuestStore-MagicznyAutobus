package com.codecool.queststore.controller.server.service;

import com.codecool.queststore.DAO.ArtifactDAO;
import com.codecool.queststore.DAO.ClassDAO;
import com.codecool.queststore.DAO.ConnectionPool;
import com.codecool.queststore.DAO.UserDAO;
import com.codecool.queststore.dao.interfaces.ArtifactDAOInterface;
import com.codecool.queststore.model.server.session.SessionPool;
import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;
import com.codecool.queststore.view.RenderInteface;
import com.codecool.queststore.view.TemplateRender;

import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class StoreService {
    private final HttpCookie cookie;
    private final String path;
    private ArtifactDAOInterface artifactDAOInterface = new ArtifactDAO(ConnectionPool.getInstance());
    private RenderInteface renderInteface = new TemplateRender();

    public StoreService(HttpCookie cookie, String path) {
        this.cookie = cookie;
        this.path = path;
    }

    public String generateResponseBody() {
        String[] splitedPath = splitURL(path);
        User currentUser = null;
        try {
            currentUser = new UserDAO().getUser(SessionPool.getSessionByUUID(UUID.fromString(cookie.getValue())).getUSER_ID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Artifact> artifacts = artifactDAOInterface.getArtifacts();
        System.out.println("rendering shop page...");
        System.out.println("current user: " + currentUser.getNAME() + " " + currentUser.getSURNAME());

        if (splitedPath.length < 3)
            return renderInteface.RenderShopPage(currentUser, artifacts);
        else
            return action(splitedPath, currentUser, artifacts);
    }

    private String action(String[] splitedPath, User currentUser, List<Artifact> artifacts) {
        final int ACTION_PLACE = 2;
        final int ACTION_TARGET = 3;

        if (splitedPath.length == 4) {
           switch (splitedPath[ACTION_PLACE]) {
               case "buy": {
                   if (currentUser.getROLE() != Role.STUDENT) {
                       System.out.println("Only students are able to buy items.");
                       return null;
                   }
                   System.out.println("action: buy");
                   if (isStringCastableToInt(splitedPath[ACTION_TARGET])) {
                       artifactDAOInterface.buyArtifact(currentUser.getID(), Integer.parseInt(splitedPath[ACTION_TARGET]));
                       System.out.println("Action success");
                   }else
                       System.out.println("error: invalid artifact id");
                   return null;
               }
               default:
                   System.out.println("error: invalid action");
                   return null;
           }
        } else {
            return null;
        }
    }

    private String[] splitURL(String path) {
        return path.split("/");
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
