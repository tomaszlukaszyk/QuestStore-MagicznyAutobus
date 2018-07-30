package com.codecool.queststore;
import com.codecool.queststore.model.server.Server;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException {
        Server server = new Server();
        try {
            server.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
