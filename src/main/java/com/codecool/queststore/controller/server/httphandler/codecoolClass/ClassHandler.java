package com.codecool.queststore.controller.server.httphandler.codecoolClass;

import com.codecool.queststore.controller.server.httphandler.AbstractHttphandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;
import java.sql.SQLException;

public class ClassHandler extends AbstractHttphandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException{
        System.out.println(httpExchange.getRequestMethod());

        try {
            handleSession(httpExchange);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleSession(HttpExchange httpExchange) throws IOException, SQLException{
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = new HttpCookie("Session-id", cookieStr);

        // Check if cookie already exists and if it's UUID is contained by sessionPool
        if (cookieStr == null || !isCookieValid(cookie)) {
            // send login page and if method request is POST add cookie etc
            redirect(httpExchange,"/");

        } else {
            ClassHelper classHelper = new ClassHelper(cookie,httpExchange.getRequestURI().getPath());
            String response = classHelper.generateResponseBody();
            SendReq(httpExchange,response);
        }
    }
}
