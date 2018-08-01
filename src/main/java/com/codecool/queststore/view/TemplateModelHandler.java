package com.codecool.queststore.view;

import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.shop.quest.Quest;
import com.codecool.queststore.model.user.User;
import org.jtwig.JtwigModel;

import java.util.ArrayList;
import java.util.List;

class TemplateModelHandler implements TemplateModelInterface {
    @Override
    public JtwigModel getClassModel(User currentUser, List<CodecoolClass> classes) {
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
    public JtwigModel getMentorsListModel(User currentUser, List<User> users, boolean isCreated) {
        JtwigModel model = new JtwigModel();
        model.with("currentUser", currentUser);
        model.with("users", users);
        model.with("listName", "Create new mentor");
        model.with("title", "Mentors List");
        model.with("isCreated", isCreated);

        return model;
    }

    @Override
    public JtwigModel getStudentsListModel(User currentUser, List<User> users, boolean isCreated) {
        JtwigModel model = new JtwigModel();
        model.with("currentUser", currentUser);
        model.with("users", users);
        model.with("listName", "Create new student");
        model.with("title", "Student list");
        model.with("isCreated", isCreated);

        return model;
    }

    @Override
    public JtwigModel getQuestModel(User currentUser, List<Quest> quests) {
        JtwigModel model = new JtwigModel();
        model.with("currentUser", currentUser);
        model.with("items", quests);
        model.with("title", "Quests");

        return model;
    }

    @Override
    public JtwigModel getArtifactModel(User currentUser, List<Artifact> artifacts) {
        JtwigModel model = new JtwigModel();
        model.with("currentUser", currentUser);
        model.with("items", artifacts);
        model.with("title", "Shop");

        return model;
    }
}
