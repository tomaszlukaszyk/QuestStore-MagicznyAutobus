package com.codecool.queststore.model.user.address;

public class NoAddress implements AddressStrategy {

    @Override
    public String getAddress() {
        return null;
    }
}
