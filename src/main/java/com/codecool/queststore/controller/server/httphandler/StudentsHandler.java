package com.codecool.queststore.controller.server.httphandler;

import com.codecool.queststore.DAO.StudentDAO;
import com.codecool.queststore.model.Login;
import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.Map;

public class StudentsHandler extends AbstractHttphandler implements HttpHandler{

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            try {
                if(httpExchange.getRequestMethod().equals("POST")){
                    handlePost(httpExchange);
                } else{
                    handleSession(httpExchange);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void handleSession(HttpExchange httpExchange) throws IOException, SQLException {
            String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
            HttpCookie cookie = new HttpCookie("Session-id", cookieStr);

            // Check if cookie already exists and if it's UUID is contained by sessionPool
            if (cookieStr == null || !isCookieValid(cookie)) {
                // send login page and if method request is POST add cookie etc
                redirect(httpExchange, "/");

            } else {
                StudentsService studentsService = new StudentsService(cookie);
                String response = studentsService.generateResponseBody(true);
                SendReq(httpExchange, response);
            }
        }

        private void handlePost(HttpExchange httpExchange) throws IOException, SQLException {
            String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
            HttpCookie cookie = new HttpCookie("Session-id", cookieStr);
            StudentsService studentsService = new StudentsService(cookie);
            Map inputs = parseFormData(httpExchange);
            StudentDAO dao = new StudentDAO();

            boolean isCreated = dao.createStudent(new User(inputs.get("studentname").toString(), inputs.get("studentsurname").toString(),
                    inputs.get("studentemail").toString(), inputs.get("studentaddress").toString(), 0, Role.STUDENT),
                    new Login(inputs.get("studentlogin").toString(), inputs.get("studentpassword").toString()), Integer.parseInt(inputs.get("studentclass").toString()));

            String response =  studentsService.generateResponseBody(isCreated);
            SendReq(httpExchange, response);
        }
    }
