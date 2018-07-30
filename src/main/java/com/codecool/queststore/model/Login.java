package com.codecool.queststore.model;

import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;

public class Login {

    private final String LOGIN;
    private final String PASSWORD;

    public Login(String login, String password) {
        this.LOGIN = login;
        this.PASSWORD = password;
    }

    public User SignIn() {
        //todo: implement with using Login DAO
        if (LOGIN.equals(PASSWORD))
            return new User("Maciek","Sikora","m@com","cracow", 0,Role.STUDENT);
        else
            return null;
    }

    public String getLOGIN() {
        return LOGIN;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }
}
