package com.codecool.queststore.view;

import com.codecool.queststore.model.Title;
import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.shop.quest.Quest;
import com.codecool.queststore.model.user.User;

import java.util.List;

public interface RenderInteface {
    String RenderClassPage(User currentUser, List<CodecoolClass> classes);
    String RenderClassPage(User currentUser, List<CodecoolClass> classes, CodecoolClass targetClass);

    // mentor profile
    String RenderProfilePage(User currentUser, User profile, List<CodecoolClass> classes);

    // student profile
    String RenderProfilePage(User currentUser, User profile, CodecoolClass ccClass, List<Artifact> items);

    // admin profile
    String RenderProfilePage(User currentUser, List<Title> titles);

    String RenderMentorListPage(User currentUser, List<User> users);

    String RenderStudentListPage(User currentUser, List<User> users);

    String RenderShopPage(User currentUser, List<Artifact> artifacts);

    String RenderQuestPage(User currentUser, List<Quest> quests);
}
