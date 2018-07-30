package com.codecool.queststore.controller.server.httphandler;

import com.codecool.queststore.view.TemplateRender;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpCookie;

public class ProfileHandler extends AbstractHttphandler implements HttpHandler {


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println(httpExchange.getRequestMethod());
        handleSession(httpExchange);

    }


    private void handleSession(HttpExchange httpExchange) throws IOException  {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = new HttpCookie("Session-id", cookieStr);
        System.out.println(cookie);

        // Check if cookie already exists and if it's UUID is contained by sessionPool
        if (cookieStr == null || !isCookieValid(cookie)) {
            // send login page and if method request is POST add cookie etc
            redirect(httpExchange,"/");

        } else {

            System.out.println("sending profile page");

            String response = new TemplateRender().RenderProfilePage();
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
}
