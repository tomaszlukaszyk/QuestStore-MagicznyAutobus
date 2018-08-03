package com.codecool.queststore.controller.server.httphandler;

import com.codecool.queststore.DAO.ClassDAO;
import com.codecool.queststore.controller.server.service.ClassService;
import com.codecool.queststore.dao.interfaces.ClassDAOInterface;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.Map;

public class ClassHandler extends AbstractHttphandler implements HttpHandler {
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
                if (httpExchange.getRequestURI().getPath().equals("/class/style.css")) {
                    return;
                }
                String path = httpExchange.getRequestURI().getPath();
                if(path.equals("/classes/add") && httpExchange.getRequestMethod().equals("POST")) {
                    ClassDAOInterface classDAO = new ClassDAO();
                    Map<String, String> parsedData = parseFormData(httpExchange);
                    classDAO.createClass(parsedData.get("name"));
                    redirect(httpExchange, "/class");
                } else {

                    System.out.println("sending class page...");
                    ClassService classService = new ClassService(cookie, httpExchange.getRequestURI().getPath());
                    String response = classService.generateResponseBody();
                    System.out.println("Success!\n\n");
                    SendReq(httpExchange, response);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
