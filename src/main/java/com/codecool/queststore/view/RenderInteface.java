package com.codecool.queststore.view;

import com.codecool.queststore.model.Title;
import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.shop.quest.Quest;
import com.codecool.queststore.model.shop.quest.QuestTemplate;
import com.codecool.queststore.model.user.User;
import org.jtwig.JtwigModel;

import java.util.List;

public interface RenderInteface {
    String RenderClassPage(User currentUser, List<CodecoolClass> classes);
    String RenderClassPage(User currentUser, List<CodecoolClass> classes, CodecoolClass targetClass);
    String RenderClassPage(User currentUser, List<CodecoolClass> classes, String message);
    String RenderClassPage(User currentUser, List<CodecoolClass> classes, List<User> users, int classID);

    // mentor profile
    String RenderProfilePage(User currentUser, User profile, List<CodecoolClass> classes);

    // student profile
    String RenderProfilePage(User currentUser, User profile, CodecoolClass ccClass, List<Artifact> items);
    String RenderProfilePage(User currentUser, List<Title> titles);

    String RenderMentorListPage(User currentUser, List<User> users, boolean isCreated);

    String RenderStudentListPage(User currentUser, List<User> users, boolean isCreated, List<CodecoolClass> classes);

    String RenderShopPage(User currentUser, List<Artifact> artifacts);

    String RenderQuestPage(User currentUser, List<Quest> quests);

    String RenderQuestTemplatesPage(User currentUser, List<QuestTemplate> quests);
    String RenderEditQuestTemplatesPage(User currentUser, List<QuestTemplate> questTemplates);

    String rendeAddQuestTemplatesPage(User currentUser);

    String renderAddClassTemplatesPage(User currentUser);
}
