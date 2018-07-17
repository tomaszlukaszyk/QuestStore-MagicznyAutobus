package com.codecool.queststore.DAO;


import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        LoginDAO login = new LoginDAO();
        System.out.println(login.validation("testStudent2", "123"));
    }
}
