package com.codecool.queststore.controller.server.httphandler;

import com.codecool.queststore.DAO.StudentDAO;
import com.codecool.queststore.DAO.UserDAO;
import com.codecool.queststore.model.server.session.SessionPool;
import com.codecool.queststore.model.user.User;
import com.codecool.queststore.view.TemplateRender;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.UUID;

public class StudentsService {

    private final HttpCookie cookie;

    StudentsService(HttpCookie cookie) {
        this.cookie = cookie;
    }

    String generateResponseBody(boolean isCreated) throws SQLException {
        User currentUser = new UserDAO().getUser(SessionPool.getSessionByUUID(UUID.fromString(cookie.getValue())).getUSER_ID());

        return new TemplateRender().RenderStudentListPage(currentUser, new StudentDAO().getStudents(), isCreated);
    }
}