package com.codecool.queststore.DAO;

import com.codecool.queststore.dao.interfaces.ClassDAOInterface;
import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.user.User;

import java.util.List;

public class ClassDAO implements ClassDAOInterface {
    @Override
    public List<CodecoolClass> getClasses() {
        return null;
    }

    private List<User> getMentors(int idClass) {return null;}
    private List<User> getStudents(int idClass) {return null;}
}
