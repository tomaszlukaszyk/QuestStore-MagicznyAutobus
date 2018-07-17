package com.codecool.queststore.model.server.session;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class SessionPool implements Serializable {

    private static Set<Session> sessions = new HashSet<>();

    public static Session getNewSession() {
        Session newSession;
        do {
         newSession= new Session();
        } while (sessions.contains(newSession));
        sessions.add(newSession);
        return newSession;
    }


    public void terminate(Session session) {
        sessions.remove(session);
    }

    public Session getSessionbyUUID( UUID uuid)
    {
        expireCheckAndClean();
        for (Session session: sessions) {
            if (session.getUuid().equals(uuid))
                return session;


        }
        return null;
    }

    private void expireCheckAndClean() {
        for(Session session: sessions)
        {
            if(session.getExpirationDate().isAfter(LocalDateTime.now()))
                terminate(session);
        }
    }


}
