package com.codecool.queststore.view;

import com.codecool.queststore.model.Title;
import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.shop.quest.Quest;
import com.codecool.queststore.model.user.User;
import org.jtwig.JtwigModel;

import java.util.List;

interface TemplateModelInterface {
    JtwigModel getClassModel(User currentUser, List<CodecoolClass> classes);
    JtwigModel getProfileStudentModel(User currentUser, User profile, CodecoolClass ccClass, List<Artifact> items);
    JtwigModel getProfileMentorModel(User currentUser, User profile, List<CodecoolClass> classes);

    JtwigModel getProfileAdminModel(User currentUser, List<Title> titles);

    JtwigModel getStudentsListModel(User currentUser, List<User> users);
    JtwigModel getMentorsListModel(User currentUser, List<User> users);

    JtwigModel getQuestModel(User currentUser, List<Quest> quests);
    JtwigModel getArtifactModel(User currentUser, List<Artifact> artifacts);
}
