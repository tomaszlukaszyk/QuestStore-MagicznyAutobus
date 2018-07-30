package com.codecool.queststore.model.user;
import com.codecool.queststore.model.user.title.*;
import com.codecool.queststore.model.user.wallet.*;

public class User {

    private final String NAME;
    private final String SURNAME;
    private final String EMAIL;
    private final String ADDRESS;
    private final int ID;
    private final Role ROLE;
    private WalletStrategy wallet;

    private TitleStrategy title;

    public User(String name, String surname, String email, String address, int id, Role role) {
        this.EMAIL = email;
        this.NAME = name;
        this.SURNAME = surname;
        this.ADDRESS = address;
        this.ID = id;
        this.ROLE = role;
        setStrategies();
    }

    public int getWallet() {
        return wallet.getWallet();
    }

    public String getADDRESS() {
        return this.ADDRESS;
    }

    public String getTitle() { return title.getTitle(); }

    private void setStrategies() {
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


    @Override
    public String toString() {
        return "User{" +
                "NAME='" + NAME + '\'' +
                ", SURNAME='" + SURNAME + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", ID=" + ID +
                ", ROLE=" + ROLE +
                ", wallet=" + wallet +
                ", address=" + ADDRESS +
                ", title=" + title +
                '}';
    }
}
