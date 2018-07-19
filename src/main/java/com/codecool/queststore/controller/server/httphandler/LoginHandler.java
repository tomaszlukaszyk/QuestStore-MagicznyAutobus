package com.codecool.queststore.controller.server.httphandler;

import com.codecool.queststore.controller.server.service.LoginService;
import com.codecool.queststore.model.server.session.Session;
import com.codecool.queststore.model.server.session.SessionPool;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpCookie;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.Map;
import java.util.UUID;


public class LoginHandler extends AbstractHttphandler implements HttpHandler {


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
            String path;


        HttpCookie cookie = new HttpCookie("Session-id", cookieStr);

        // Check if cookie already exists and if it's UUID is contained by sessionPool
        if (cookieStr == null || !SessionPool.isSessionbyCookie(cookie)) {
            // send login page and if method request is POST add cookie etc
            System.out.println("sending login page");
            path = "html/index_test.html";
            URL fileURL = getClass().getClassLoader().getResource(path);
            sendFile(httpExchange, fileURL);
        } else {
            httpExchange.getResponseHeaders().set("Location", "profile");
            httpExchange.sendResponseHeaders(302, -1); // execute redirect
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
            httpExchange.getResponseHeaders().set("Location", "profile");
            httpExchange.sendResponseHeaders(302, -1); // execute redirect
            System.out.println("generated cookie \\/");
            System.out.println(cookie.toString());
        }
        else{
            System.out.println("sending login page");
            String path = "html/index_test.html";
            URL fileURL = getClass().getClassLoader().getResource(path);
            sendFile(httpExchange, fileURL);
        }

    }


}
