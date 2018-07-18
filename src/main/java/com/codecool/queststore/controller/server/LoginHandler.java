package com.codecool.queststore.controller.server;

import com.codecool.queststore.controller.server.helpers.MimeTypeResolver;
import com.codecool.queststore.model.server.Server;
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

    SessionPool sessionPool = Server.getSessionPool();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");

        // Check if cookie already exists and if it's UUID is contained by sessionPool
        if (cookieStr != null || sessionPool.getSessionbyUUID(UUID.fromString(HttpCookie.parse(cookieStr).get(0).getValue())) != null) {
            // redirect further
        } else {
            // send login page and if method request is POST add cookie etc

            /** String path = "html/login.html";
             URL fileURL = getClass().getClassLoader().getResource(path);
             sendFile(httpExchange, fileURL); **/
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
        while ((count = fs.read(buffer)) >= 0) {
            os.write(buffer, 0, count);
        }
        os.close();
    }
}
