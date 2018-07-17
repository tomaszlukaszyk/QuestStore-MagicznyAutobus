package com.codecool.queststore.model.user;

public class Login {

    private final String LOGIN;
    private final String PASSWORD;

    public Login(String login, String password) {
        this.LOGIN = login;
        this.PASSWORD = password;
    }

    public User signIn() {

        //todo: Implement

        //if(validate())
        //    return new UserDAO().getUser(LOGIN);
        //else
            return null;

    }

    private void validate(){
        //todo: Implement
    };

}
