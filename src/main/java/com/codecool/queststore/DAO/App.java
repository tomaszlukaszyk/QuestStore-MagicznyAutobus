package com.codecool.queststore.DAO;
import com.codecool.queststore.model.Login;
import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        LoginDAO login = new LoginDAO();
        UserDAO user = new UserDAO();
        Login loginn = new Login("testStudent2", "123");
        System.out.println(login.validation(loginn));
        user.deleteUser(1);
        System.out.println(user.getUser(2).toString());
        User userr = new User("nie", "nie", "nie", "nie", 3, Role.STUDENT);
        user.updateUser(userr);
    }
}
