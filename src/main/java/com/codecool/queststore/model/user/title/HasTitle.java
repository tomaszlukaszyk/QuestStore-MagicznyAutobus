package com.codecool.queststore.model.user.title;

import com.codecool.queststore.model.user.User;

public class HasTitle implements TitleStrategy {
    private final User user;

    public HasTitle(User user) {
        this.user = user;
    }

    @Override
    public String getTitle() {
     //todo: Implement
        return null;
    }
}
