package com.codecool.queststore.controller.server;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;


public class TestHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("you have been redirected to profile");
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        System.out.println("i see ur cookie \\/");
        System.out.println(cookieStr);

        String response = "<html><body>" +
                "<h1> Witam w profilu xD </h1>" +
                "</body></html>";
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
