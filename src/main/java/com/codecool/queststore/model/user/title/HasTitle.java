package com.codecool.queststore.model.user.title;

import com.codecool.queststore.DAO.TitleDAO;
import com.codecool.queststore.model.Title;
import com.codecool.queststore.model.user.User;

public class HasTitle implements TitleStrategy {
    private final User user;

    public HasTitle(User user) {
        this.user = user;
    }

    @Override
    public String getTitle() {
        Title title = new TitleDAO().getUserTitle(user.getID());
        System.out.println(title.getNAME());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
     return title.getNAME();
    }
}
