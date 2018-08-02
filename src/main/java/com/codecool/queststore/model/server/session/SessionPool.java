package com.codecool.queststore.model.server.session;

import java.io.*;
import java.net.HttpCookie;
import java.time.LocalDateTime;
import java.util.*;

public class SessionPool {

    private static final String FILENAME = "src/main/resources/Sessions.pool";
    private static Set<Session> sessions = new HashSet<>();

    public SessionPool() {
        if(sessions.isEmpty())
            try {
                sessions = readObject();
            } catch (IOException e) {
                System.out.println("there was no data file, so sessions will be empty");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    public static Session getNewSession(int userId) {
        Session newSession;
        do {
         newSession= new Session(userId);
        } while (sessions.contains(newSession));
        sessions.add(newSession);
        System.out.println("created Session ID " + newSession.getUuid());

        writeObject();
        return newSession;
    }


    public static void terminate(Session session) {
        sessions.remove(session);
        System.out.println("removed Session ID " + session.getUuid());
        writeObject();
    }

    public static Session getSessionByUUID( UUID uuid)
    {
        expireCheckAndClean();
        for (Session session: sessions) {
            if (session.getUuid().equals(uuid))
                session.RenewExpirationDate();
                return session;


        }
        return null;
    }

    public static boolean isSessionByCookie(HttpCookie cookie)
    {
        expireCheckAndClean();
        for (Session session: sessions) {
            if (session.getUuid().toString().equals(cookie.getValue())){
                System.out.println("found session ID: " + session.getUuid());
                return true;}


        }

            System.out.println("not found session by cookie: " + cookie);

        return false;
    }

    private static void expireCheckAndClean() {
        for(Session session: sessions)
        {
            if(session.getExpirationDate().isBefore(    LocalDateTime.now()))
                terminate(session);
        }

    }

    public static void writeObject() {
        try{
            FileOutputStream fos= new FileOutputStream(FILENAME);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(sessions);
            oos.close();
            fos.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private Set<Session> readObject() throws ClassNotFoundException, IOException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME));
        return (Set<Session>) ois.readObject();
    }
}
