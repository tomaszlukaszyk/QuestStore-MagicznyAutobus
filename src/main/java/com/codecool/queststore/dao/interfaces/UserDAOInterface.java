package com.codecool.queststore.dao.interfaces;

import com.codecool.queststore.model.user.User;
import java.sql.SQLException;

public interface UserDAOInterface {
    void deleteUser(int id) throws SQLException;
    void updateUser(User user) throws SQLException;
    User getUser(int id) throws SQLException;
}
