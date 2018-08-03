package com.codecool.queststore.DAO;

import com.codecool.queststore.dao.interfaces.UserDAOInterface;
import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        String name = null;
        String surname = null;
        String email = null;
        String address = null;
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

        if (email == null)
            return null;
        return new User(name, surname, email, address, id, role);
    }

    @Override
    public List<User> getUsers() {
        try {
        List<User> users = new ArrayList<>();
        int id = 0;
        String name = null;
        String surname = null;
        String email = null;
        String address = null;
        Role role = null;
        Connection conn = cp.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM getusers()");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            id = rs.getInt(1);
            name = rs.getString(2);
            surname = rs.getString(3);
            email = rs.getString(4);
            address = rs.getString(5);
            role = Role.getRoleByName(rs.getString(6));

            if (email != null)
                users.add(new User(name, surname, email, address, id, role));
        }
        stmt.close();
        conn.close();
        return users;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getUsers(Role role) {
        try {
            List<User> users = new ArrayList<>();

            String roleStr = role.getNAME();
            if (roleStr == null) return null;

            int id = 0;
            String name = null;
            String surname = null;
            String email = null;
            String address = null;

            Connection conn = cp.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM getusers(?)");
            stmt.setString(1, roleStr);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2);
                surname = rs.getString(3);
                email = rs.getString(4);
                address = rs.getString(5);

                if (email != null)
                    users.add(new User(name, surname, email, address, id, role));
            }
            stmt.close();
            conn.close();
            return users;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateEmail(int id, String email) {
        try {
            Connection conn = cp.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT updateEmail(?, ?)");
            stmt.setInt(1, id);
            stmt.setString(2, email);
            stmt.executeQuery();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

        }

        public void updateAddress(int id, String address) throws SQLException {
        Connection conn = cp.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT updateAddress(?, ?)");
        stmt.setInt(1, id);
        stmt.setString(2, address);
        stmt.executeQuery();
        stmt.close();
        conn.close();


    }
}
