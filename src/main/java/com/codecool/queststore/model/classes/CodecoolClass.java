package com.codecool.queststore.model.classes;

import com.codecool.queststore.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class CodecoolClass {
    private final int ID;
    private final String NAME;
    private final List<User> ASSIGNED_MENTORS;
    private final List<User> ASSIGNED_STUDENTS;

    public CodecoolClass(int id, String name, List<User> assignedMentors, List<User> assignedStudents) {
        this.ID = id;
        this.NAME = name;
        this.ASSIGNED_MENTORS = assignedMentors;
        this.ASSIGNED_STUDENTS = assignedStudents;
        //from dao to class
    }

    public int getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }

    public List<User> getASSIGNED_MENTORS() {
        return ASSIGNED_MENTORS;
    }

    public List<User> getASSIGNED_STUDENTS() {
        return ASSIGNED_STUDENTS;
    }
}
