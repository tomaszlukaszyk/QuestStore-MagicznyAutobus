package com.codecool.queststore.controller.server.service;

import com.codecool.queststore.model.Login;
import com.codecool.queststore.model.server.session.Session;
import com.codecool.queststore.model.server.session.SessionPool;
import com.codecool.queststore.model.user.User;

public class LoginService {

    private final Session session;

    public LoginService(String name, String password) {
        Login login = new Login(name, password);
        User user = login.SignIn();
        if (user != null)
            this.session = SessionPool.getNewSession(user.getID());
        else
            this.session = null;
    }

    public Session getSession(){
    return this.session;
    }

}
