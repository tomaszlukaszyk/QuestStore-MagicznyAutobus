package com.codecool.queststore.model.user;

public class User {

    private final String NAME;
    private final String SURNAME;
    private final String EMAIL;
    private final int ID;

    public User(String name, String surname, String email, int id) {
        this.EMAIL = email;
        this.NAME = name;
        this.SURNAME = surname;
        this.ID = id;
    }

}
