package com.codecool.queststore.controller.server.httphandler;

import com.codecool.queststore.controller.server.service.QuestService;
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
                QuestService questService = new QuestService();
                questService.handleSession(httpExchange);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
