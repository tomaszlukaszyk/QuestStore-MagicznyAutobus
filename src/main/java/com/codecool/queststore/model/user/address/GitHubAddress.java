package com.codecool.queststore.model.user.address;

import com.codecool.queststore.model.user.User;

public class GitHubAddress implements AddressStrategy {

    private final User user;

    public GitHubAddress(User user) {
        this.user = user;
    }


    @Override
    public String getAddress() {
        return null;
    }
}
