package com.codecool.queststore.controller.server;

import com.codecool.queststore.controller.server.helpers.MimeTypeResolver;
import com.codecool.queststore.model.server.session.Session;
import com.codecool.queststore.model.server.session.SessionPool;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.URL;
import java.util.UUID;


public class LoginHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        // checks user input after using login form
        if(httpExchange.getRequestMethod().equals("POST")){
            // check DB for user and password
            boolean password = true;
            if(password){
                HttpCookie cookie = SessionPool.getNewSession().getCookie();
                System.out.println("generated cookie \\/");
                System.out.println(cookie);
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString()); // give user cookie

                httpExchange.getResponseHeaders().set("Location", "profile");
                httpExchange.sendResponseHeaders(302, -1); // execute redirect
                return;
            }
        }

        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        // Check if cookie already exists and if it's UUID is contained by sessionPool
        if (cookieStr == null || SessionPool.getSessionbyUUID(UUID.fromString(HttpCookie.parse(cookieStr).get(0).getValue())) == null) {
            // send login page and if method request is POST add cookie etc
            System.out.println("sending login page");
            String path = "html/index_test.html";
            URL fileURL = getClass().getClassLoader().getResource(path);
            sendFile(httpExchange, fileURL);
        } else {
            System.out.println("redirecting");
            httpExchange.getResponseHeaders().set("Location", "profile");
            httpExchange.sendResponseHeaders(302, -1);
        }
    }

    private void sendFile(HttpExchange httpExchange, URL fileURL) throws IOException {
        // get the file
        File file = new File(fileURL.getFile());
        // we need to find out the mime type of the file, see: https://en.wikipedia.org/wiki/Media_type
        MimeTypeResolver resolver = new MimeTypeResolver(file);
        String mime = resolver.getMimeType();

        httpExchange.getResponseHeaders().set("Content-Type", mime);
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();

        // send the file
        FileInputStream fs = new FileInputStream(file);
        final byte[] buffer = new byte[0x10000];
        int count = 0;
        while ((count = fs.read(buffer))>= 0) {
            os.write(buffer, 0, count);
        }
        os.close();
    }
}
