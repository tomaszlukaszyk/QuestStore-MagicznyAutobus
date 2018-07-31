package com.codecool.queststore.controller.server.httphandler;

import com.codecool.queststore.DAO.MentorDAO;
import com.codecool.queststore.model.Login;
import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.Map;

public class MentorsHandler extends AbstractHttphandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if(httpExchange.getRequestMethod().equals("POST")){
                handlePost(httpExchange);
            } else{
                handleSession(httpExchange);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        private void handleSession(HttpExchange httpExchange) throws IOException, SQLException {
            String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
            HttpCookie cookie = new HttpCookie("Session-id", cookieStr);

            // Check if cookie already exists and if it's UUID is contained by sessionPool
            if (cookieStr == null || !isCookieValid(cookie)) {
                // send login page and if method request is POST add cookie etc
                redirect(httpExchange, "/");

            } else {
                MentorsService mentorsService = new MentorsService(cookie);
                String response = mentorsService.generateResponseBody(true);
                SendReq(httpExchange, response);
            }
        }

        private void handlePost(HttpExchange httpExchange) throws IOException, SQLException {
            String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
            HttpCookie cookie = new HttpCookie("Session-id", cookieStr);
            MentorsService mentorsService = new MentorsService(cookie);
            Map inputs = parseFormData(httpExchange);
            MentorDAO dao = new MentorDAO();
            boolean isCreated = dao.createMentor(new User(inputs.get("mentorname").toString(), inputs.get("mentorsurname").toString(),
                                      inputs.get("mentoremail").toString(), inputs.get("mentoraddress").toString(), 0, Role.MENTOR),
                                        new Login(inputs.get("mentorlogin").toString(), inputs.get("mentorpassword").toString()));

            String response =  mentorsService.generateResponseBody(isCreated);
            SendReq(httpExchange, response);
        }
    }

