package com.codecool.queststore.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO implements Connectable {

    public void deleteUser(int userId) throws SQLException {
        Connection conn = cp.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT deleteUser(?)");
        stmt.setInt(1, userId);
        stmt.executeQuery();
        stmt.close();
        conn.close();
    }
}
