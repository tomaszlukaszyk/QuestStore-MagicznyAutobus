package com.codecool.queststore.model.classes;

import com.codecool.queststore.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class CodecoolClass {

    private final String NAME;
    private final List<User> ASSIGNED_MENTORS;
    private final List<User> ASSIGNED_STUDENTS;

    public CodecoolClass(String name, List<User> assignedMentors, List<User> assignedStudents) {
        this.NAME = name;
        this.ASSIGNED_MENTORS = assignedMentors;
        this.ASSIGNED_STUDENTS = assignedStudents;
        //from dao to class
    }

    public CodecoolClass(String name) {
     this.NAME = name;
     this.ASSIGNED_MENTORS = new ArrayList<>();
     this.ASSIGNED_STUDENTS = new ArrayList<>();
     //empty class Todo: write it to database!!
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
