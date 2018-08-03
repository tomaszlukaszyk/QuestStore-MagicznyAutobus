package com.codecool.queststore;

import com.codecool.queststore.model.server.Server;

import java.sql.SQLException;

public class App {


    public static void main(String[] args) throws SQLException {

      /* GroupTransaction transaction = new GroupTransaction(new UserDAO().getUser(3),new ArtifactDAO().getArtifact(2));
        transaction.addParticipant(new UserDAO().getUser(4));
        transaction.addParticipant(new UserDAO().getUser(5));
        transaction.finalize();*/
        Server server = new Server();
        try {
            server.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
