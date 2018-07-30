package com.codecool.queststore;

import com.codecool.queststore.model.server.Server;

public class App {

    public static void main(String[] args){
        Server server = new Server();
        try {
            server.run();
        }catch (Exception e){
            e.getStackTrace();
        }
    }

}
