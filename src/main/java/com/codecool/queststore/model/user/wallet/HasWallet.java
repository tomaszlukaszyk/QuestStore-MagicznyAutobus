package com.codecool.queststore.model.user.wallet;

import com.codecool.queststore.model.user.User;

public class HasWallet implements WalletStrategy {

    private final User user;

    public HasWallet(User user) {
        this.user = user;
    }


    @Override
    public Integer getWallet() {
        //todo: implement
        return null;
    }

}
