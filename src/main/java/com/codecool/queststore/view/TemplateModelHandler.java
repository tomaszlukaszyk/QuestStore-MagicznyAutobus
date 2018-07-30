package com.codecool.queststore.view;

import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.shop.quest.Quest;
import com.codecool.queststore.model.user.User;
import org.jtwig.JtwigModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TemplateModelHandler implements TemplateModelInterface {
    @Override
    public JtwigModel getClassModel(User user, CodecoolClass ccClass) {
        return null;
    }

    @Override
    public JtwigModel getProfileStudentModel(User currentUser, User profile, CodecoolClass ccClass, List<Artifact> items) {
        JtwigModel model = new JtwigModel();
        model.with("currentUser", currentUser);
        model.with("profile", profile);
        model.with("ccClass", ccClass);
        model.with("items", items);
        model.with("title", "Profile");

        return model;
    }

    @Override
    public JtwigModel getProfileMentorModel(User currentUser, User profile, List<CodecoolClass> classes) {
        JtwigModel model = new JtwigModel();
        model.with("currentUser", currentUser);
        model.with("profile", profile);
        model.with("classes", classes);
        model.with("students", collectStudents(classes));
        model.with("title", "Profile");

        return model;
    }

    private List<User> collectStudents(List<CodecoolClass> classes) {
        List<User> students = new ArrayList<>();

        for (CodecoolClass c : classes) {
            students.addAll(c.getASSIGNED_STUDENTS());
        }

        return students;
    }

    @Override
    public JtwigModel getUserListModel(User logedUser, List<User> users) {
        return null;
    }

    @Override
    public JtwigModel getQuestModel(User logedUser, List<Quest> quests) {
        return null;
    }

    @Override
    public JtwigModel getArtifactModel(User logedUser, List<Artifact> artifacts) {
        return null;
    }
}
