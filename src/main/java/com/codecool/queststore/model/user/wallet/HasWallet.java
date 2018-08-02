package com.codecool.queststore.model.user.wallet;

import com.codecool.queststore.DAO.WalletDAO;
import com.codecool.queststore.model.user.User;

import java.sql.SQLException;

public class HasWallet implements WalletStrategy {

    private final User user;

    public HasWallet(User user) {
        this.user = user;
    }


    @Override
    public Integer getWallet() {
        try {
            return new WalletDAO().getWalletExceptGroupArtifacts(user.getID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
