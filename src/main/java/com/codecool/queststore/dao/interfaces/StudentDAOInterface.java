package com.codecool.queststore.dao.interfaces;

import com.codecool.queststore.model.Login;
import com.codecool.queststore.model.user.User;

import java.sql.SQLException;
import java.util.List;

public interface StudentDAOInterface {

    boolean createStudent(User user, Login login, int classId) throws SQLException;

    List<User> getStudents() throws SQLException;
}
