package com.codecool.queststore.controller.server.httphandler;

import com.codecool.queststore.controller.server.service.ClassService;
import com.codecool.queststore.controller.server.service.StoreService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;
import java.sql.SQLException;

public class StoreHandler extends AbstractHttphandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException{
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
            try {
                System.out.println("sending shop page...");
                StoreService storeService = new StoreService(cookie, httpExchange.getRequestURI().getPath());
                String response = storeService.generateResponseBody();
                if (response != null)
                    SendReq(httpExchange, response);
                else
                    redirect(httpExchange, "/shop");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }}
