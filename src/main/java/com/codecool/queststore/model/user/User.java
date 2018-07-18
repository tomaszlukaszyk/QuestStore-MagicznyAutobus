package com.codecool.queststore.model.user;

import com.codecool.queststore.model.user.address.*;
import com.codecool.queststore.model.user.wallet.*;

public class User {

    private final String NAME;
    private final String SURNAME;
    private final String EMAIL;
    private final int ID;
    private final Role ROLE;
    private WalletStrategy wallet;
    private AddressStrategy address;

    public User(String name, String surname, String email, int id, Role role) {
        this.EMAIL = email;
        this.NAME = name;
        this.SURNAME = surname;
        this.ID = id;
        ROLE = role;
        setStrategies();
    }

    public void getWallet() {
        wallet.getWallet();
    }

    public void getAddress() {
        address.getAddress();
    }

    private void setStrategies() {
        setAddressStrategy();
        setWalletStrategy();
    }

    private void setWalletStrategy() {
        switch (ROLE){
            case STUDENT:
                this.wallet = new HasWallet(this);
                break;
            default:
                this.wallet = new NoWallet();
        }
    }

    private void setAddressStrategy() {
        switch (ROLE){
            case STUDENT:
                this.address = new GitHubAddress(this);
                break;
            case MENTOR:
                this.address = new StreetAddress(this);
                break;
            default:
                this.address = new NoAddress();
        }
    }
}
