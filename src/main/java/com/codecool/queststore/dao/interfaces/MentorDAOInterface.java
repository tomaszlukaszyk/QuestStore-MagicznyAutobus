package com.codecool.queststore.dao.interfaces;

import com.codecool.queststore.model.Login;
import com.codecool.queststore.model.user.User;

import java.sql.SQLException;

public interface MentorDAOInterface {
    boolean createMentor(User user, Login login) throws SQLException;
}
