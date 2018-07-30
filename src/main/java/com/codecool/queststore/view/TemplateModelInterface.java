package com.codecool.queststore.view;

import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.shop.quest.Quest;
import com.codecool.queststore.model.user.User;
import org.jtwig.JtwigModel;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Map;

interface TemplateModelInterface {
    JtwigModel getClassModel(User user, CodecoolClass ccClass);
    JtwigModel getProfileStudentModel(User currentUser, User profile, CodecoolClass ccClass, List<Artifact> items);
    JtwigModel getProfileMentorModel(User logedUser, User user, List<CodecoolClass> classes);
    JtwigModel getUserListModel(User logedUser, List<User> users);
    JtwigModel getQuestModel(User logedUser, List<Quest> quests);
    JtwigModel getArtifactModel(User logedUser, List<Artifact> artifacts);
}
