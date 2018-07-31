package com.codecool.queststore.controller.server.httphandler;

import com.codecool.queststore.controller.server.service.LogoutService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;

public class LogoutHandler extends AbstractHttphandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = new HttpCookie("Session-id", cookieStr);
        System.out.println("logout");

        new LogoutService(cookie);
        redirect(httpExchange,"/");
    }
}
