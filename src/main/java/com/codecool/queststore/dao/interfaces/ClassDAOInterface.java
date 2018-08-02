package com.codecool.queststore.dao.interfaces;

import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.user.User;

import java.util.List;

public interface ClassDAOInterface {
    List<CodecoolClass> getClasses();
    boolean assignMentor(int userID, int classID);
}
