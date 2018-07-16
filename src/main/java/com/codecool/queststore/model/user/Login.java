package com.codecool.queststore.model.user;

public class Login {

    private final String login;
    private final String password;

    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User signIn() {

        //todo: Implement

        //if(validate())
        //    return new UserDAO().getUser(login);
        //else
            return null;

    }

    private void validate(){
        //todo: Implement
    };

}
