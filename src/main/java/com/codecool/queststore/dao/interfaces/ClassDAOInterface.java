package com.codecool.queststore.dao.interfaces;

import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.user.User;

import java.sql.SQLException;
import java.util.List;

public interface ClassDAOInterface {
    List<CodecoolClass> getClasses() throws SQLException;
    List<User> getMentorsFromClass(int classId) throws SQLException;
    List<User> getStudentsFromClass(int classId) throws SQLException;
}
