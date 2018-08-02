package com.codecool.queststore.view;

import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.shop.quest.Quest;
import com.codecool.queststore.model.shop.quest.QuestTemplate;
import com.codecool.queststore.model.user.User;
import org.jtwig.JtwigModel;

import java.util.List;

public interface RenderInteface {
    String RenderClassPage();

    // mentor profile
    String RenderProfilePage(User currentUser, User profile, List<CodecoolClass> classes);

    // student profile
    String RenderProfilePage(User currentUser, User profile, CodecoolClass ccClass, List<Artifact> items);

    String RenderMentorListPage(User currentUser, List<User> users);

    String RenderStudentListPage(User currentUser, List<User> users);

    String RenderShopPage(User currentUser, List<Artifact> artifacts);

    String RenderQuestPage(User currentUser, List<Quest> quests);

    String RenderQuestTemplatesPage(User currentUser, List<QuestTemplate> quests);
    String RenderEditQuestTemplatesPage(User currentUser, List<QuestTemplate> questTemplates);

    String rendeAddQuestTemplatesPage(User currentUser);
}
