package com.codecool.queststore;

import com.codecool.queststore.DAO.UserDAO;
import com.codecool.queststore.model.Login;
import com.codecool.queststore.model.server.Server;
import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;

import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException {
        Server server = new Server();
        try {
            server.run();
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }

        UserDAO dao = new UserDAO();
        Login login = new Login("baba", "lolo");
        User user = new User("tak", "tak", "tak", "cracow", 0, Role.MENTOR);



    }

}
