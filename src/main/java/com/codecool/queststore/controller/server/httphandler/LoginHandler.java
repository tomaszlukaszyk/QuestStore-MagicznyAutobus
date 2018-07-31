package com.codecool.queststore.controller.server.httphandler;

import com.codecool.queststore.controller.server.service.LoginService;
import com.codecool.queststore.model.server.session.Session;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpCookie;
import java.net.URL;
import java.util.Map;

public class LoginHandler extends AbstractHttphandler implements HttpHandler {
    private final String path = "html/index.html";

        @Override
    public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println(httpExchange.getRequestMethod());
        // checks user input after using login form

        if(httpExchange.getRequestMethod().equals("POST")){
            handlePost(httpExchange);
        } else{
            handleSession(httpExchange);
        }
    }

    private void handleSession(HttpExchange httpExchange) throws IOException  {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = new HttpCookie("Session-id", cookieStr);

        // Check if cookie already exists and if it's UUID is contained by sessionPool
        if (cookieStr == null || !isCookieValid(cookie)) {
            // send login page and if method request is POST add cookie etc
            System.out.println("sending login page");
            URL fileURL = getClass().getClassLoader().getResource(path);
            sendFile(httpExchange, fileURL);
        } else {
            redirect(httpExchange,"/profile/");
        }
    }

    private void handlePost(HttpExchange httpExchange) throws IOException {
        Map inputs = parseFormData(httpExchange);
        // check DB for user and password
        LoginService service = new LoginService((String) inputs.get("login"), (String) inputs.get("password"));
        Session session = service.getSession();

        if (session != null) {
            HttpCookie cookie = session.getCookie();
            httpExchange.getResponseHeaders().add("Set-Cookie", cookie.getValue());
            redirect(httpExchange,"profile");
            System.out.println("generated cookie \\/");
            System.out.println(cookie.toString());
        } else {
            System.out.println("sending login page");
            URL fileURL = getClass().getClassLoader().getResource(path);
            sendFile(httpExchange, fileURL);
        }
    }
}