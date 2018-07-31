package com.codecool.queststore.controller.server.httphandler.profile;

import com.codecool.queststore.DAO.UserDAO;
import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.server.session.SessionPool;
import com.codecool.queststore.model.user.User;
import com.codecool.queststore.view.TemplateRender;

import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class ProfileHelper {
    private final HttpCookie cookie;
    private final String path;


    public ProfileHelper(HttpCookie cookie, String path) {
        this.cookie = cookie;
        this.path = path;

    }

    public String generateResponseBody() throws SQLException {
        User currentUser = new UserDAO().getUser(SessionPool.getSessionByUUID(UUID.fromString(cookie.getValue())).getUSER_ID());
        User targetUser = defineTarget(currentUser, path);
        switch(targetUser.getROLE()) {
            case STUDENT:
                //todo: fill class and artifacts by theirs daos
                return new TemplateRender().RenderProfilePage(currentUser, targetUser, new CodecoolClass("LOL"), new ArrayList<>());


            case MENTOR:
                //todo: fill classes by their daos
                return new TemplateRender().RenderProfilePage(currentUser, targetUser, new ArrayList<>());


            default:
                //todo: there is no admin view
                return new TemplateRender().RenderProfilePage(currentUser, targetUser, new CodecoolClass("LOL"), new ArrayList<>());




        }

    }

    private User defineTarget(User currentUser, String path) throws SQLException {
        Integer userID = getTargetUserID(splitURL(path));
        User target = null;
        if (userID != null)
                target = new UserDAO().getUser(userID);
        if (target != null)
            return target;
        else
        return currentUser;

    }

    private String[] splitURL(String path) {
        return path.split("/");
    }
    private Integer getTargetUserID(String[] array) {
        final int USER_ID_PLACE = 2;
        if(array.length > USER_ID_PLACE)
            if(isStringCastableToInt(array[USER_ID_PLACE]))
                return Integer.parseInt(array[USER_ID_PLACE]);

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
