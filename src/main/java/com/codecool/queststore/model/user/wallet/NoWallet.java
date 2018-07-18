package com.codecool.queststore.model.user.wallet;

import com.codecool.queststore.model.user.User;

public class NoWallet implements WalletStrategy{
    @Override
    public Integer getWallet() {
        return null;
    }
}
