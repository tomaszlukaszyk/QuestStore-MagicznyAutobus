package com.codecool.queststore.model.server;

import com.codecool.queststore.controller.server.*;

import com.codecool.queststore.model.server.session.SessionPool;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private static SessionPool sessionPool = new SessionPool();

    public void run() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        setContext(server);
        server.setExecutor(null);
        server.start();
    }

    // Overload to run server on desired port
    public void run(int port) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        setContext(server);
        server.setExecutor(null);
        server.start();
    }

    private void setContext(HttpServer server){
        server.createContext("/", new LoginHandler());

    }

    public static SessionPool getSessionPool() {
        return sessionPool;
    }
}
