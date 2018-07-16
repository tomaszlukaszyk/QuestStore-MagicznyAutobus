package com.codecool.queststore.model.classes;

import com.codecool.queststore.model.user.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CodecoolClass {

    private final String name;
    private final List<User> assignedMentors;
    private final List<User> assignedStudents;

    public CodecoolClass(String name, List<User> assignedMentors, List<User> assignedStudents) {
        this.name = name;
        this.assignedMentors = assignedMentors;
        this.assignedStudents = assignedStudents;
        //from dao to class
    }

    public CodecoolClass(String name) {
     this.name = name;
     this.assignedMentors = new ArrayList<>();
     this.assignedStudents = new ArrayList<>();
     //empty class Todo: write it to database!!
    }


}
