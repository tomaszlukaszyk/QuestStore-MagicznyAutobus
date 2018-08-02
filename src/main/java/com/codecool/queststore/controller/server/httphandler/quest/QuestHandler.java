package com.codecool.queststore.controller.server.httphandler.quest;

import com.codecool.queststore.controller.server.httphandler.AbstractHttphandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;
import java.sql.SQLException;

public class QuestHandler extends AbstractHttphandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = new HttpCookie("Session-id", cookieStr);

        // Check if cookie already exists, if it's UUID is contained by sessionPool and if role is mentor
        if (cookieStr == null || !isCookieValid(cookie)) {
            redirect(httpExchange, "/");
        } else {
            try {
                QuestHelper questHelper = new QuestHelper();
                questHelper.handleSession(httpExchange);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
