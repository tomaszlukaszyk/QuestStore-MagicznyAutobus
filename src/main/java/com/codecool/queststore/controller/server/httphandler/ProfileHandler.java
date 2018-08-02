package com.codecool.queststore.controller.server.httphandler;

import com.codecool.queststore.controller.server.service.ProfileService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;
import java.sql.SQLException;

public class ProfileHandler extends AbstractHttphandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println(httpExchange.getRequestMethod());

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
    private void handlePost(HttpExchange httpExchange) throws IOException, SQLException {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = new HttpCookie("Session-id", cookieStr);

        // Check if cookie already exists and if it's UUID is contained by sessionPool
        if (cookieStr == null || !isCookieValid(cookie)) {
            redirect(httpExchange,"/");

        } else {
            new ProfileService(cookie,httpExchange.getRequestURI().getPath()).handlePost(parseFormData(httpExchange));
            redirect(httpExchange,"/profile");
        }
    }


    private void handleSession(HttpExchange httpExchange) throws IOException, SQLException {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = new HttpCookie("Session-id", cookieStr);

        // Check if cookie already exists and if it's UUID is contained by sessionPool
        if (cookieStr == null || !isCookieValid(cookie)) {
            redirect(httpExchange,"/");

        } else {
            ProfileService profileHelper = new ProfileService(cookie,httpExchange.getRequestURI().getPath());
            String response = profileHelper.generateResponseBody();
            SendReq(httpExchange,response);
        }
    }
}
