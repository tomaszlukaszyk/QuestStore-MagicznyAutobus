package com.codecool.queststore.dao;

import java.sql.SQLException;

/**
 * Interface using Credentials to gather necessary data.
 * it creates contract to whatever class implements it to have
 * methods for opening connections and closing them.
 */
public interface PostgreDBC {

    String username = Credentials.getDbLogin();
    String password = Credentials.getDbPasswd();
    String dbUrl = Credentials.getDbUrl();

    void connect() throws SQLException;
    void close() throws SQLException;



}