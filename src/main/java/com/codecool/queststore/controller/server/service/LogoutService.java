package com.codecool.queststore.controller.server.service;

import com.codecool.queststore.model.server.session.Session;
import com.codecool.queststore.model.server.session.SessionPool;

import java.net.HttpCookie;
import java.util.UUID;

public class LogoutService {

    private final Session session;

    public LogoutService(HttpCookie cookie) {
            this.session = SessionPool.getSessionByUUID(UUID.fromString(cookie.getValue()));
            System.out.println("terminated");
            SessionPool.terminate(session);
    }

}
