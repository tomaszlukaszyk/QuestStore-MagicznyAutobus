package com.codecool.queststore.controller.server.httphandler;

import com.codecool.queststore.controller.server.helpers.MimeTypeResolver;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.codecool.queststore.model.server.session.SessionPool;
import com.sun.net.httpserver.HttpExchange;

import java.net.URL;


    abstract class AbstractHttphandler {

        boolean isCookieValid(HttpCookie cookie) {
             return SessionPool.isSessionByCookie(cookie);

        }


        Map<String, String> parseFormData(HttpExchange httpExchange) throws IOException {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map<String, String> map = new HashMap<>();
            String[] pairs = formData.split("&");
            for(String pair : pairs){
                String[] keyValue = pair.split("=");
                // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
                String value = new URLDecoder().decode(keyValue[1], "UTF-8");
                map.put(keyValue[0], value);
            }
            return map;
        }

        void sendFile(HttpExchange httpExchange, URL fileURL) throws IOException {
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
