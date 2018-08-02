package com.codecool.queststore.dao.interfaces;

import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;
import java.sql.SQLException;
import java.util.List;

public interface UserDAOInterface {
    void deleteUser(int id) throws SQLException;
    boolean updateEmail(int id, String email);
    void updateAddress(int id, String address) throws SQLException;
    User getUser(int id) throws SQLException;
    List<User> getUsers();
    List<User> getUsers(Role role);
}
