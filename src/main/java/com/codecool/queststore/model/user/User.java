package com.codecool.queststore.model.user;

import com.codecool.queststore.model.user.address.*;
import com.codecool.queststore.model.user.title.*;
import com.codecool.queststore.model.user.wallet.*;

public class User {

    private final String NAME;
    private final String SURNAME;
    private final String EMAIL;
    private final int ID;
    private final Role ROLE;
    private WalletStrategy wallet;
    private AddressStrategy address;
    private TitleStrategy title;

     User(String name, String surname, String email, int id, Role role) {
        this.EMAIL = email;
        this.NAME = name;
        this.SURNAME = surname;
        this.ID = id;
        this.ROLE = role;
        setStrategies();
    }

    public int getWallet() {
        return wallet.getWallet();
    }

    public String getAddress() {
        return address.getAddress();
    }

    public String getTitle() { return title.getTitle(); }



    private void setStrategies() {
        setAddressStrategy();
        setWalletStrategy();
        setTitleStrategy();
    }

    public String getNAME() {
        return NAME;
    }

    public String getSURNAME() {
        return SURNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public int getID() {
        return ID;
    }

    public Role getROLE() {
        return ROLE;
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

    private void setTitleStrategy() {
        switch (ROLE){
            case STUDENT:
                this.title = new HasTitle(this);
                break;
            default:
                this.title = new NoTitle();
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
