package com.codecool.queststore.DAO;

import com.codecool.queststore.dao.interfaces.ClassDAOInterface;
import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO implements ClassDAOInterface, Connectable {
    @Override
    public List<CodecoolClass> getClasses() throws SQLException {
        List<CodecoolClass> classes = new ArrayList<>();
        Connection conn = cp.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM getClasses()");

        while (rs.next()) {
            classes.add(new CodecoolClass(rs.getInt("id"), rs.getString("description"),
                        getMentorsFromClass(rs.getInt("id")), getStudentsFromClass(rs.getInt("id"))));
        }

        rs.close();
        conn.close();

        return classes;
    }

    public List<User> getMentorsFromClass(int classId) throws SQLException{
        List<User> result = new ArrayList<>();
        Connection conn = cp.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM getClassMentors(?)");
        stmt.setInt(1, classId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("userid");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String email = rs.getString("email");
            String address = rs.getString("address");

            result.add(new User(name, surname, email, address, id, Role.MENTOR));
        }
        rs.close();
        conn.close();
        return result;
    }

    public List<User> getStudentsFromClass(int classId) throws SQLException {
        List<User> result = new ArrayList<>();
        Connection conn = cp.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM getClassStudents(?)");
        stmt.setInt(1, classId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("userid");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String email = rs.getString("email");
            String address = rs.getString("address");

            result.add(new User(name, surname, email, address, id, Role.STUDENT));
        }
        rs.close();
        conn.close();
        return result;
    }
}