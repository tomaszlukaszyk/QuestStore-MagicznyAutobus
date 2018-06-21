package com.codecool.queststore.dbcreation;

import com.codecool.queststore.dao.interfaces.PostgreDBC;

import java.sql.SQLException;
import java.sql.*;

/**
 * Class DbHandler is a quick implementation of connection to database and execute preparedUpdates.
 * it is implemented only for purpose of initializing empty database.
 * <p>
 * It's final and package only. Should not be used anywhere else.
 */
final class DbHandler implements PostgreDBC {


    static private Connection conn = null;

    public void connect() throws SQLException {
        if( conn == null) {
            conn = DriverManager.getConnection(dbUrl, username, password);
            return;}

        else
        if(conn.isClosed())
            conn = DriverManager.getConnection(dbUrl, username, password);

    }

    @Override
    public void close() throws SQLException {
        if(conn != null)
            if(!conn.isClosed())
                conn.close();
    }


    public void executeUpdate(String update) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(update);

    }


}
