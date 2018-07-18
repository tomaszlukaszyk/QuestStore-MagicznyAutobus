package com.codecool.queststore.model.server.session;

import java.io.*;
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
        writeObject();
        return newSession;
    }


    private static void terminate(Session session) {
        sessions.remove(session);
        writeObject();
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

    private Set<Session> readObject() throws ClassNotFoundException, IOException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME));
        return (Set<Session>) ois.readObject();
    }
}
