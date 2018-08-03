package com.codecool.queststore.model.user;

public class UserFactory {

    public User fromId(int id) {
        //todo: create dao to get data and create user
    return null;
    }

    public User fromData(String name, String surname, String email, int id, String address, Role role) {
        return new User(name, surname, email, address, id, role);
    }

}
