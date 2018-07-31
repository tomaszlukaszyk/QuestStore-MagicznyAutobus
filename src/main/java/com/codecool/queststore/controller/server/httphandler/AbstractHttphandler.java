package com.codecool.queststore.controller.server.httphandler;

import com.codecool.queststore.controller.server.helpers.MimeTypeResolver;
import com.codecool.queststore.model.server.session.SessionPool;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.HttpCookie;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


public abstract class AbstractHttphandler {

        protected boolean isCookieValid(HttpCookie cookie) {
             return SessionPool.isSessionByCookie(cookie);
        }

        protected void redirect(HttpExchange httpExchange, String path) throws IOException {
            final int NOW = -1;
            httpExchange.getResponseHeaders().set("Location", path);
            httpExchange.sendResponseHeaders(ResponsesEnum.REDIRECT.getCode(), NOW);
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

    protected void SendReq(HttpExchange httpExchange, String response) throws IOException {
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
