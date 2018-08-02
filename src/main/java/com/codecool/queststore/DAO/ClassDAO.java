package com.codecool.queststore.DAO;

import com.codecool.queststore.dao.interfaces.ClassDAOInterface;
import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO implements ClassDAOInterface, Connectable{
    private Connection conn = null;
    @Override
    public List<CodecoolClass> getClasses() {
        List<CodecoolClass> classes = new ArrayList<>();
        try {
            conn = cp.getConnection();
            cp.printDbStatus();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM getClasses()");

            while (rs.next()) {
                int classID = rs.getInt("id");
                String name = rs.getString("description");
                classes.add(new CodecoolClass(classID, name, getMentors(classID), getStudents(classID)));
            }
            rs.close();
            stmt.close();
            conn.close();
            return classes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean assignMentor(int userID, int classID) {
        try {
            Connection conn = cp.getConnection();
            cp.printDbStatus();
            PreparedStatement statement = conn.prepareStatement("select idmentor from mentor where iduser=?");
            statement.setInt(1, userID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int mentorID = rs.getInt(1);

                PreparedStatement stmt = conn.prepareStatement("INSERT INTO mentor_class (idmentor, idclass) VALUES (?, ?)");
                stmt.setInt(1, mentorID);
                stmt.setInt(2, classID);
                stmt.executeUpdate();
                stmt.close();
            }
            rs.close();
            statement.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<User> getMentors(int idClass) throws SQLException{
        List<User> users = new ArrayList<>();
        cp.printDbStatus();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM getClassMentors(?)");
        stmt.setInt(1, idClass);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("userid");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String email = rs.getString("email");
            String address = rs.getString("address");

            users.add(new User(name, surname, email, address, id, Role.MENTOR));
        }

        rs.close();
        stmt.close();
        return users;
    }

    private List<User> getStudents(int idClass) throws SQLException{
        List<User> users = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM getClassStudents(?)");
        stmt.setInt(1, idClass);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("userid");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String email = rs.getString("email");
            String address = rs.getString("address");

            users.add(new User(name, surname, email, address, id, Role.STUDENT));
        }
        rs.close();
        stmt.close();
        return users;
    }
}