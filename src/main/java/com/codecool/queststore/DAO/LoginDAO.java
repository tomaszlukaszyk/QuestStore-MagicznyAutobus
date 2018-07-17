package com.codecool.queststore.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDAO  implements ConnectionInterface {

    private final static int USER_ID = 1;

    public int validation(String userName, String password) throws SQLException {
        int result = 0;
        Connection conn = cp.getConnection();
        cp.printDbStatus();
        PreparedStatement stmt = conn.prepareStatement("SELECT validation(?, ?)");
        stmt.setString(1, userName);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            result = rs.getInt(USER_ID);
        }
        rs.close();
        stmt.close();
        conn.close();
        cp.printDbStatus();

        return result;
    }

}

