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

    public int getWallet() {
        //todo: Implement using UserDAO
        //if role is student ->
        //return studentDAO->getwallet;
        //else
        return null;
    }

    public String getTitle() {
        //todo: Implement using UserDAO
        //if role is student ->
        //return ""; as above
        //else
        return null;
    }

    public String getAddress() {
        //todo: Implement using UserDAO
        //if user is student or mentor ->
        //return ""; as above
        //else
        return null;
    }

}
