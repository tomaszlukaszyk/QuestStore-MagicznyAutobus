package com.codecool.queststore.model.server.session;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class SessionPool implements Serializable {

    private static Set<Session> sessions = new HashSet<>();

    public static Session getNewSession(int userId) {
        Session newSession;
        do {
         newSession= new Session(userId);
        } while (sessions.contains(newSession));
        sessions.add(newSession);
        return newSession;
    }


    private static void terminate(Session session) {
        sessions.remove(session);
    }

    public static Session getSessionbyUUID( UUID uuid)
    {
        expireCheckAndClean();
        for (Session session: sessions) {
            if (session.getUuid().equals(uuid))
                return session;


        }
        return null;
    }

    private static void expireCheckAndClean() {
        for(Session session: sessions)
        {
            if(session.getExpirationDate().isBefore(LocalDateTime.now()))
                terminate(session);
        }
    }


}
