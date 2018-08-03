package com.codecool.queststore.model.user.wallet;

public class NoWallet implements WalletStrategy{
    @Override
    public Double getWallet() {
        return null;
    }
}
