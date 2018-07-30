package com.codecool.queststore.DAO;

import com.codecool.queststore.dao.interfaces.UserDAOInterface;
import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements Connectable, UserDAOInterface {

    @Override
    public void deleteUser(int userId) throws SQLException {
        Connection conn = cp.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT deleteUser(?)");
        stmt.setInt(1, userId);
        stmt.executeQuery();
        stmt.close();
        conn.close();
    }

    @Override
    public User getUser(int id) throws SQLException {
        String name = "";
        String surname = "";
        String email = "";
        String address = "";
        Role role = null;
        Connection conn = cp.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM getUser(?)");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            name = rs.getString(1);
            surname = rs.getString(2);
            email = rs.getString(3);
            address = rs.getString(4);
            role = Role.getRoleByName(rs.getString(5));
        }
        stmt.close();
        conn.close();
        return new User(name, surname, email, address, id, role);
    }

    @Override
    public void updateUser(User user) throws SQLException {
        Connection conn = cp.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT updateUser(?, ?, ?, ?, ?)");
        stmt.setInt(1, user.getID());
        stmt.setString(2, user.getSURNAME());
        stmt.setString(3, user.getNAME());
        stmt.setString(4, user.getEMAIL());
        stmt.setString(5, user.getADDRESS());
        stmt.executeQuery();
        stmt.close();
        conn.close();
    }
}
