package com.codecool.queststore.model.user.address;

import com.codecool.queststore.model.user.User;

public class StreetAddress implements AddressStrategy {

    private final User user;

    public StreetAddress(User user) {
        this.user = user;
    }


    @Override
    public String getAddress() {
        return null;
    }
}
