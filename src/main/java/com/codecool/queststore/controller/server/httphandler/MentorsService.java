package com.codecool.queststore.controller.server.httphandler;

import com.codecool.queststore.DAO.MentorDAO;
import com.codecool.queststore.DAO.UserDAO;
import com.codecool.queststore.model.server.session.SessionPool;
import com.codecool.queststore.model.user.User;
import com.codecool.queststore.view.TemplateRender;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.UUID;

public class MentorsService {

    private final HttpCookie cookie;


    MentorsService(HttpCookie cookie) {
        this.cookie = cookie;
    }

        String generateResponseBody() throws SQLException {
            User currentUser = new UserDAO().getUser(SessionPool.getSessionByUUID(UUID.fromString(cookie.getValue())).getUSER_ID());

            return new TemplateRender().RenderListPage(currentUser, new MentorDAO().getMentors());
        }
    }

