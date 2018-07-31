package com.codecool.queststore.controller.server.httphandler.profile;

import com.codecool.queststore.controller.server.httphandler.AbstractHttphandler;
import com.codecool.queststore.controller.server.httphandler.ResponsesEnum;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpCookie;
import java.sql.SQLException;

public class ProfileHandler extends AbstractHttphandler implements HttpHandler {


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println(httpExchange.getRequestMethod());
        try {
            handleSession(httpExchange);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private void handleSession(HttpExchange httpExchange) throws IOException, SQLException {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = new HttpCookie("Session-id", cookieStr);
        System.out.println(cookie);

        // Check if cookie already exists and if it's UUID is contained by sessionPool
        if (cookieStr == null || !isCookieValid(cookie)) {
            // send login page and if method request is POST add cookie etc
            redirect(httpExchange,"/");

        } else {
            ProfileHelper profileHelper = new ProfileHelper(cookie,httpExchange.getRequestURI().getPath());
            String response = profileHelper.generateResponseBody();
            SendReq(httpExchange,response);

        }
    }

    private void SendReq(HttpExchange httpExchange, String response) throws IOException {
        Reader rawString = new StringReader(response);
        BufferedReader result = new BufferedReader(rawString);
        httpExchange.sendResponseHeaders(ResponsesEnum.OK.getCode(), response.length());
        OutputStream os = httpExchange.getResponseBody();
        try {
            String str;
            while ((str = result.readLine()) != null) {
                os.write(str.getBytes());
            }
            result.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        os.close();
        System.out.println(response);

    }
}
